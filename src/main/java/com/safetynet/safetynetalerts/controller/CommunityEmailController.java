package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.ICommunityEmailDTO;
import com.safetynet.safetynetalerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommunityEmailController {

    private Logger logger = LoggerFactory.getLogger(CommunityEmailController.class);

    @Autowired
    private PersonService personService;

    /**
     * Response to url http://localhost:9090/communityEmail?city=<city>.
     * @param city the city for which we need all the inhabitants email
     * @return the email list of the city inhabitants
     */
    @GetMapping("/communityEmail")
    public List<ICommunityEmailDTO> getEmails(@RequestParam("city") String city) {
        if (city.equals("")) {
            logger.error("URL /communityEmail Request: a parameter \"city\" is needed");
            return null;
        } else {
            logger.info("URL /communityEmail: Request for "+ city + " received");
            return personService.getEmailList(city);
        }
    }
}