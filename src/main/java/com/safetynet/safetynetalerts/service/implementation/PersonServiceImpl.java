package com.safetynet.safetynetalerts.service.implementation;

import com.safetynet.safetynetalerts.model.DTO.ICommunityEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.IPhoneAlertDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import com.safetynet.safetynetalerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Override
    public Optional<Person> updatePerson(Person person) {
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

    @Override
    public boolean getPersonId(PersonId personId) {
        return personRepository.findById(personId).isPresent();
    }
    @Override
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

    @Override
    public Iterable<Person> savePersonList(List<Person> persons) {
        return personRepository.saveAll(persons);
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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
