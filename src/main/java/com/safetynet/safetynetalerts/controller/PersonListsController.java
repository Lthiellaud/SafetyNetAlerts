package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.PersonDto;
import com.safetynet.safetynetalerts.model.IPersonEmail;
import com.safetynet.safetynetalerts.service.PersonListsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class PersonListsController {

    private Logger logger = LoggerFactory.getLogger(PersonListsController.class);
    @Autowired
    PersonListsService personListsService;

    @GetMapping("/communityEmail")
    public Iterable<IPersonEmail> getEmails(@RequestParam("city") String city) {
        if (city ==null || city == "") {
            logger.error("URL /communityEmail Request : a parameter \"city\" is needed");
            return null;
        }
        List<IPersonEmail> emailList = personListsService.getEmailList(city);
        if (emailList.size() > 0){
            logger.info("URL /communityEmail Request : Email of all inhabitants of " + city + " sent");
            return personListsService.getEmailList(city);
        } else {
            logger.error("URL /communityEmail Request : no inhabitants found for city " +
                     city);
            return null;
        }
    }

    @GetMapping("/fire")
    public List<PersonDto> getFirePersons(@RequestParam("address") String address) {
        return personListsService.getFirePersonList(address);
    }

}
