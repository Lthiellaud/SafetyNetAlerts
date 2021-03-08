package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.ICommunityEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.IPhoneAlertDTO;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    /**
     * To apply the modifications to a person. Only non null attributes will be changed.
     * @param person The modified person record to be used for updating the person
     * @return the founded person or an empty optional if nothing done
     */
    public Optional<Person> updatePerson(Person person){
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
            LOGGER.debug("updatePerson: record for " + personId.toString() + " updated");
            return Optional.of(currentPerson);
        } else {
            LOGGER.debug("updatePerson: no record found for " + personId.toString());
            return p;
        }

    }

    /**
     * To verify if the person defined by firstname and lastname exists.
     * @param personId defines firstname and lastname of the person
     * @return true if the person has been found, false otherwise
     */
    public boolean getPersonId(PersonId personId){
        return personRepository.findById(personId).isPresent();
    }
    /**
     * To add a person.
     * @param person the person to be added
     * @return the saved person or an empty optional if nothing done
     */
    public Optional<Person> createPerson(Person person) {
        PersonId personId = new PersonId(person.getFirstName(), person.getLastName());
        if (!getPersonId(personId)) {
            LOGGER.debug("createPerson: record for " + personId.toString() + " created");
            personRepository.save(person);
            return Optional.of(person);
        } else {
            LOGGER.debug("createPerson: " + personId.toString() + " already existing");
            return Optional.empty();
        }
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
     * @return true if a record has been deleted, false otherwise
     */
    public boolean deletePerson(String firstName, String lastName) {
        PersonId personId = new PersonId(firstName, lastName);
        if (getPersonId(personId)) {
            LOGGER.debug("record for " + personId.toString() + " deleted");
            personRepository.deleteById(personId);
            return true;
        } else {
            LOGGER.debug("deletePerson: " + personId.toString() + " does not exists. " +
                    "Nothing done");
            return false;
        }

    }

    /**
     * To get the email list of all the inhabitants of a city.
     * @param city The city for which an email list is needed
     * @return the email list
     */
    public List<ICommunityEmailDTO> getEmailList(String city) {
        List<ICommunityEmailDTO> persons = personRepository.findAllDistinctEmailByCity(city);
        int i = persons.size();
        if (i > 0) {
            LOGGER.debug("getEmailList: List of " + i + " person(s) found for the city " + city);
            return persons;
        } else {
            LOGGER.debug("getEmailList:nobody found for the city " + city);
            return new ArrayList<>();
        }

    }

    /**
     * To get all the phone numbers of corresponding to a list of address.
     * @param addresses the list of addresses for which we need the phone number
     * @return the list of phone number
     */
    public List<IPhoneAlertDTO> getPhones(List<String> addresses) {
        List<IPhoneAlertDTO> persons = personRepository
                .findAllDistinctPhoneByAddressIsIn(addresses);
        int i = persons.size();
        if (i > 0) {
            LOGGER.debug("getPhones: List of " + i + " person(s) found for the addresses " +
                    addresses);
            return persons;
        } else {
            LOGGER.debug("getPhones:nobody found for the addresses " + addresses);
            return new ArrayList<>();
        }
    }

    /**
     * to get the list of the inhabitants at the given address.
     * @param address The address for which we need the inhabitants list
     * @return the inhabitants list in PersonMedicalRecordDTO projection
     */
    public List<PersonMedicalRecordDTO> getAllByAddress(String address) {
        List<PersonMedicalRecordDTO> persons = personRepository.findAllByAddress(address);
        int i = persons.size();
        if (i > 0) {
            LOGGER.debug("getAllByAddress: List of " + i + " person(s) found for the address " +
                    address);
            return persons;
        } else {
            LOGGER.debug("getAllByAddress:nobody found for the address " + address);
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
    public List<PersonMedicalRecordDTO>
                getAllByFirstAndLastName(String firstName, String lastName) {
        if (lastName != null) {
            List<PersonMedicalRecordDTO> personsByLastName =
                    personRepository.findAllByLastName(lastName);
            int i = personsByLastName.size();
            if (i > 0) {
                if (firstName == null || firstName.equals("")) {
                    LOGGER.debug("getAllByFirstAndLastName: List of " + i + " person(s) with " +
                            "lastname " + lastName + " found");
                    return personsByLastName;
                } else {
                    List<PersonMedicalRecordDTO> personsByFirstAndLastName = personsByLastName
                            .stream()
                            .filter(p -> p.getFirstName().matches(firstName))
                            .collect(Collectors.toList());
                    i = personsByFirstAndLastName.size();
                    if (i > 0) {
                        LOGGER.debug("getAllByFirstAndLastName: List of " + i + " person(s) with " +
                                "name " + firstName + " " + lastName + " found");
                        return personsByFirstAndLastName;
                    }
                }
            }
        } else {
            LOGGER.debug("last name is mandatory");
            return new ArrayList<>();
        }
        LOGGER.debug("getAllByFirstAndLastName: nobody with lastname " +
                firstName + " " + lastName + " found");
        return new ArrayList<>();
    }

}
