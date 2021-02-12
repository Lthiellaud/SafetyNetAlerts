package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.DTO.FirePersonDTO;
import com.safetynet.safetynetalerts.model.DTO.IPersonEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonWithPhoneDTO;
import com.safetynet.safetynetalerts.service.PersonListsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
public class PersonListsController {

    private Logger logger = LoggerFactory.getLogger(PersonListsController.class);
    @Autowired
    PersonListsService personListsService;

    ObjectMapper objectMapper;



    @GetMapping("/communityEmail")
    public Iterable<IPersonEmailDTO> getEmails(@RequestParam("city") String city) {
        if (city ==null || city == "") {
            logger.error("URL /communityEmail Request : a parameter \"city\" is needed");
            return null;
        }
        List<IPersonEmailDTO> emailList = personListsService.getEmailList(city);
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
    @ResponseBody
    public FirePersonDTO getFirePersons(@RequestParam("address") String address) {
        System.out.println("go throw");
        PersonWithPhoneDTO personWithPhoneDTO = new PersonWithPhoneDTO("P1", "N1", "Ph1");
        FirePersonDTO firePersonDTO = new FirePersonDTO(Arrays.asList(1,2), Arrays.asList(personWithPhoneDTO));

        return firePersonDTO;//personListsService.getFirePersonList(address);
    }

}
