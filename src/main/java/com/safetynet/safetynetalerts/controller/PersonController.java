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
    public Optional<Person> createPerson(@RequestBody Person person) {
        logger.info("Endpoint /person: creation request Person record for " + person.getFirstName() +
                  " " + person.getLastName() + " received");
        return personService.createPerson(person);
    }

    /**
     * To delete a person.
     * @param firstName the firstname of the person to be deleted
     * @param lastName the lastname of the person to be deleted
     */
    @DeleteMapping(value = "/person/{firstName}:{lastName}")
    public void deletePerson(@PathVariable("firstName") String firstName,
                             @PathVariable("lastName") String lastName) {
        logger.info("Endpoint /person: deletion request for record Person " + firstName +
                " " + lastName + " received");
        personService.deletePerson(firstName, lastName);

    }

    /**
     * To update a person.
     * The person to be updated is in the request body
     * @return the updated person
     */
    @PutMapping(value="/person")
    public Optional<Person> updatePerson(@RequestBody Person person  ) {
        logger.info("Endpoint /person: creation request for " +
                person.getFirstName() + " " + person.getLastName() + " received");
        return personService.updatePerson(person);
    }
}
