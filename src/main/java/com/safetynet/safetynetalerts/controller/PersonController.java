package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * Defines the endpoint /person.
 * Implemented actions : Post/Put/Delete
 */
@RestController
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    /**
     * To add a new person.
     * @param person the person to be added
     * @return the added person and the http status of the request
     */
    @PostMapping(value = "/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        if (!person.getFirstName().equals("") && !person.getLastName().equals("")) {
            LOGGER.info("Endpoint /person: creation request Person record for "
                    + person.getFirstName() +
                    " " + person.getLastName() + " received");
            Optional<Person> person1 = personService.createPerson(person);
            if (person1.isPresent()) {
                LOGGER.info("Endpoint /person: creation done");
                return new ResponseEntity<>(person1.get(), HttpStatus.CREATED);
            } else {
                LOGGER.info("Endpoint /person: person already existing");
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            LOGGER.error("Endpoint /person Create request: firstname and lastname mandatory");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * To delete a person.
     * @param firstName the firstname of the person to be deleted
     * @param lastName the lastname of the person to be deleted
     * @return http status of the request
     */
    @DeleteMapping(value = "/person/{firstName}:{lastName}")
    public ResponseEntity<?> deletePerson(@PathVariable("firstName") String firstName,
                                          @PathVariable("lastName") String lastName) {
        if (!firstName.equals("")  && !lastName.equals("")) {
            LOGGER.info("Endpoint /person: deletion request for record Person " + firstName +
                    " " + lastName + " received");
            if (personService.deletePerson(firstName, lastName)) {
                LOGGER.info("Endpoint /person: deletion completed");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                LOGGER.info("Endpoint /person: person not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {
            LOGGER.error("Endpoint /person delete request: firstname and lastname mandatory");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * To update a person.
     * @param person The person to be updated is in the request body
     * @return the updated person and the http status of the request
     */
    @PutMapping(value = "/person")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        if (!person.getFirstName().equals("") && !person.getLastName().equals("")) {
            LOGGER.info("Endpoint /person: update request for " +
                    person.getFirstName() + " " + person.getLastName() + " received");
            Optional<Person> person1 = personService.updatePerson(person);
            if (person1.isPresent()) {
                LOGGER.info("Endpoint /person: update done");
                return new ResponseEntity<>(person1.get(), HttpStatus.OK);
            } else {
                LOGGER.info("Endpoint /person: person not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            LOGGER.error("Endpoint /person Update request: firstname and lastname mandatory");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
