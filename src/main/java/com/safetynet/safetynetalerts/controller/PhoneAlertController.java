package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.*;
import com.safetynet.safetynetalerts.service.PhoneAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class PhoneAlertController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneAlertController.class);
    @Autowired
    private PhoneAlertService phoneAlertService;



    /**
     * URL /phoneAlert?firestation=<firestation_number>.
     * @param fireStation the number of the fire Station we need
     * @return the phone number list of the persons attached to fire station number "fireStation"
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<IPhoneAlertDTO>>
                 getPhoneList(@RequestParam("firestation") Integer fireStation) {
        List<IPhoneAlertDTO> phones = phoneAlertService.getPhones(fireStation);
        if (phones.size() != 0) {
            LOGGER.info("URL /phoneAlert request: List of inhabitants phone for fire station " +
                    fireStation + " sent");
            return new ResponseEntity<>(phones, HttpStatus.OK);
        } else {
            LOGGER.info("URL /phoneAlert request: no inhabitants found for fire station " +
                    fireStation);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
