package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.IPersonPhone;
import com.safetynet.safetynetalerts.service.FireStationListsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationListsController {

    private static Logger logger = LoggerFactory.getLogger(FireStationController.class);
    @Autowired
    private FireStationListsService fireStationListsService;

    @GetMapping("/phoneAlert")
    public Iterable<IPersonPhone> getPhoneList(@RequestParam("firestation") Integer fireStation) {
        Iterable<IPersonPhone> phones = fireStationListsService.getPhones(fireStation);
        logger.info("something done");
        return phones;
    }
}
