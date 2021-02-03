package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@RestController
public class PersonController {

    @Autowired
    private PersonService personService;
    
    @PostMapping(value="/person")
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @DeleteMapping(value = "/person/{firstName}:{lastName}")
    public void deletePerson(@PathVariable("firstName") String firstName,
                             @PathVariable("lastName") String lastName) {
        personService.deletePerson(new PersonId(firstName, lastName));
    }
    
    @PutMapping(value="/person/{firstName}:{lastName}")
    public Person updatePerson(@PathVariable("firstName") String firstName,
                               @PathVariable("lastName") String lastName ,
                               @RequestBody Person person  ) {
        Optional<Person> p = personService.getPerson(new PersonId(firstName, lastName));
        if (p.isPresent()) {
            Person currentPerson = p.get();

            String address = person.getAddress();
            if (address != null) {
                currentPerson.setAddress(address);
            }
            String city = person.getCity();
            if (city != null) {
                currentPerson.setAddress(city);
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
            return currentPerson;
        } else {
            return null;
        }
    }
}
