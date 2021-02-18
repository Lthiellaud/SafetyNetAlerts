package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.controller.PersonController;
import com.safetynet.safetynetalerts.model.DTO.IPersonEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.IPersonPhoneDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    /**
     * To apply the modifications to a person. Only non null attributes will be changed.
     * @param person The modified person record to be used for updating the person
     * @return the founded person
     */
    public Optional<Person> updatePerson(Person person){
        if (person.getFirstName() != null || person.getLastName() != null) {
            PersonId personId = new PersonId(person.getFirstName(), person.getLastName());
            Optional<Person> p = personRepository.findById(personId);
            if (p.isPresent()) {
                Person currentPerson = p.get();
                String address = person.getAddress();
                if (address != null) {
                    currentPerson.setAddress(address);
                }
                String city = person.getCity();
                if (city != null) {
                    currentPerson.setCity(city);
                }
                Integer zip = person.getZip();
                if (zip != null) {
                    currentPerson.setZip(zip);
                }
                String phone = person.getPhone();
                if (phone != null) {
                    currentPerson.setPhone(phone);
                }
                String email = person.getEmail();
                if (email != null) {
                    currentPerson.setEmail(email);
                }
                personRepository.save(currentPerson);
                logger.info("updatePerson: record for " + personId.toString() + " updated");
                return Optional.of(currentPerson);
            } else {
                logger.error("updatePerson: no record found for " + personId.toString());
                return p;
            }
        } else {
            logger.error("person update impossible : firstname and lastname mandatory");
            return Optional.of(new Person());
        }
    }

    /**
     * To verify if the person defined by firstname and lastname exists.
     * @param personId defines firstname and lastname of the person
     * @return the founded person
     */
    public boolean getPersonId(PersonId personId){
        return personRepository.findById(personId).isPresent();
    }
    /**
     * To add a person.
     * @param person the person to be added
     * @return the saved person
     */
    public Optional<Person> createPerson(Person person) {
        if (person.getFirstName() != null  && person.getLastName() != null) {
            PersonId personId = new PersonId(person.getFirstName(), person.getLastName());
            if (!getPersonId(personId)) {
                logger.info("createPerson: record for " + personId.toString() + " created");
                personRepository.save(person);
                return Optional.of(person);
            } else {
                logger.error("createPerson: " + personId.toString() + " already existing");
            }
        } else {
            logger.error("person creation impossible : firstname and lastname mandatory");
        }
        return Optional.empty();
    }

    /**
     * To add a list of persons.
     * @param persons the list of persons to be added
     * @return the saved person
     */
    public Iterable<Person> savePersonList(List<Person> persons) {
        return personRepository.saveAll(persons);
    }

    /**
     * To delete a person from his firstname and lastname.
     * @param firstName defines firstname of the person
     * @param lastName defines lastname of the person
     */
    public void deletePerson(String firstName, String lastName) {
        if (firstName != null  && lastName != null) {
            PersonId personId = new PersonId(firstName, lastName);
            if (getPersonId(personId)) {
                logger.info("record for " + personId.toString() +" deleted");
                personRepository.deleteById(personId);
            } else {
                logger.error("deletePerson: " + personId.toString() + " does not exists. " +
                        "Nothing done");
            }
        } else {
            logger.error("deletePerson: firstname and lastname mandatory");
        }
    }

    /**
     * To get the email list of all the inhabitants of a city.
     * @param city The city for which an email list is needed
     * @return the email list
     */
    public List<IPersonEmailDTO> getEmailList(String city) {
        List<IPersonEmailDTO> persons = personRepository.findAllDistinctEmailByCity(city);
        int i = persons.size();
        if (i > 0) {
            logger.info("getEmailList: List of " + i + " email sent for the city " + city);
            return persons;
        } else {
            logger.error("getEmailList:nobody found for the city " + city);
            return new ArrayList<>();
        }

    }

    /**
     * To get all the phone numbers of corresponding to a list of address
     * @param addresses the list of addresses for which we need the phone number
     * @return the list of phone number
     */
    public List<IPersonPhoneDTO> getPhones(List<String> addresses) {
        List<IPersonPhoneDTO> persons = personRepository.findAllDistinctPhoneByAddressIsIn(addresses);
        int i = persons.size();
        if (i > 0) {
            logger.info("getPhones: List of " + i + " email sent for the addresses " + addresses);
            return persons;
        } else {
            logger.error("getPhones:nobody found for the addresses " + addresses);
            return new ArrayList<>();
        }
    }

    /**
     * to get the list of the inhabitants at the given address
     * @param address The address for which we need the inhabitants list
     * @return the inhabitants list in PersonMedicalRecordDTO projection
     */
    public List<PersonMedicalRecordDTO> getAllByAddress(String address) {
        List<PersonMedicalRecordDTO> persons = personRepository.findAllByAddress(address);
        int i = persons.size();
        if (i > 0) {
            logger.info("getAllByAddress: List of " + i + " email sent for the address " + address);
            return persons;
        } else {
            logger.error("getAllByAddress:nobody found for the address " + address);
            return new ArrayList<>();
        }
    }

    /**
     * To get the list of persons with the given firstName and lastName.
     * If firstName value is omitted, all persons with the given lastName will be sent
     * @param firstName firstName value is optional
     * @param lastName lastName value is mandatory
     * @return the list of persons including address, age, email and medical information
     */
    public List<PersonMedicalRecordDTO> getAllByFirstAndLastName(String firstName, String lastName) {
        if (lastName != null) {
            List<PersonMedicalRecordDTO> personsByLastName =
                    personRepository.findAllByLastName(lastName);
            int i = personsByLastName.size();
            if (i > 0) {
                if (firstName == null || firstName.equals("")) {
                    logger.info("getAllByFirstAndLastName: List of " + i + " persons with lastname " +
                            lastName + " send");
                    return personsByLastName;
                } else {
                    List<PersonMedicalRecordDTO> personsByFirstAndLastName = personsByLastName.stream()
                            .filter(p -> p.getFirstName().matches(firstName)).collect(Collectors.toList());
                    i = personsByFirstAndLastName.size();
                    if (i > 0) {
                        logger.info("getAllByFirstAndLastName: List of " + i + " persons with name " +
                                firstName + " " + lastName + " send");
                        return personsByFirstAndLastName;
                    }
                }
            }
        } else {
            logger.error("last name is mandatory");
            return new ArrayList<>();
        }
        logger.info("getAllByFirstAndLastName: nobody with lastname " +
                firstName + " " + lastName + " found");
        return new ArrayList<>();
    }

}
