package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.ICommunityEmailDTO;
import com.safetynet.safetynetalerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Defines the URL /communityEmail{@literal ?}city={city}.
 */
@RestController
public class CommunityEmailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommunityEmailController.class);

    @Autowired
    private PersonService personService;

    /**
     * Response to url /communityEmail?city={city}.
     * @param city the city for which we need all the inhabitants email
     * @return the email list of the city inhabitants
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<List<ICommunityEmailDTO>> getEmails(@RequestParam("city") String city) {
        if (city.equals("")) {
            LOGGER.error("URL /communityEmail Request: a parameter \"city\" is needed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            LOGGER.info("URL /communityEmail: Request for " + city + " received");
            List<ICommunityEmailDTO> communityEmailList = personService.getEmailList(city);
            int i = communityEmailList.size();
            if (i > 0) {
                LOGGER.info("URL /communityEmail: " + i + " mail sent for " + city);
                return new ResponseEntity<>(communityEmailList, HttpStatus.OK);
            } else {
                LOGGER.info("URL /communityEmail: no mail found for " + city);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }
    }
}
