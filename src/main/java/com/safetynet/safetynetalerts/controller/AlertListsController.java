package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.*;
import com.safetynet.safetynetalerts.service.AlertListsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AlertListsController {

    private Logger logger = LoggerFactory.getLogger(AlertListsController.class);
    @Autowired
    AlertListsService alertListsService;



    /**
     * URL /phoneAlert?firestation=<firestation_number>.
     * @param fireStation the number of the fire Station we need
     * @return the phone number list of the persons attached to fire station number "fireStation"
     */
    @GetMapping("/phoneAlert")
    public Iterable<IPhoneAlertDTO> getPhoneList(@RequestParam("firestation") Integer fireStation) {
        List<IPhoneAlertDTO> phones = alertListsService.getPhones(fireStation);
        if (phones.size() != 0) {
            logger.info("URL /phoneAlert request: List of inhabitants phone for fire station " +
                    + fireStation + " sent");
            return phones;
        } else {
            logger.error("URL /phoneAlert request: no inhabitants found for fire station " +
                    fireStation);
            return null;
        }
    }

}
