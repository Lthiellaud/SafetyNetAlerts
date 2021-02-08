package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


/**
 * Defines the endpoint /person.
 * Implemented actions : Post/Put/Delete
 */
@RestController
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    /**
     * To add a new person.
     * @param person the person to be added
     * @return the added person
     */
    @PostMapping(value="/person")
    public Person createPerson(@RequestBody Person person) {
        PersonId personId= new PersonId(person.getFirstName(), person.getLastName());
        logger.info("record for " + personId.toString() + " added");
        return personService.savePerson(person);
    }

    /**
     * To delete a person.
     * @param firstName the firstname of the person to be deleted
     * @param lastName the lastname of the person to be deleted
     */
    @DeleteMapping(value = "/person/{firstName}:{lastName}")
    public void deletePerson(@PathVariable("firstName") String firstName,
                             @PathVariable("lastName") String lastName) {
        PersonId personId = new PersonId(firstName, lastName);
        Optional<Person> p = personService.getPerson(personId);
        if (p.isPresent()) {
            logger.info("record for " + personId.toString() +" deleted");
            personService.deletePerson(personId);
        } else {
            logger.error(personId.toString() + " : This person does not exists");
        }
    }

    /**
     * To update a person.
     * The person to be updated is in the request body
     * @return the updated person
     */
    @PutMapping(value="/person/")
    public Person updatePerson(@RequestBody Person person  ) {
        PersonId personId = new PersonId(person.getFirstName(), person.getLastName());
        Optional<Person> p = personService.getPerson(personId);
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
            personService.savePerson(currentPerson);
            logger.info("record for " + personId.toString() + " updated");
            return currentPerson;
        } else {
            logger.error("record for " + personId.toString() + " does not exist");
            return null;
        }
    }
}
