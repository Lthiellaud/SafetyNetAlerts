package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.FireDTO;
import com.safetynet.safetynetalerts.service.FireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FireController {
    @Autowired
    private FireService fireService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FireController.class);
    /**
     * Response to URL /fire?address=<address>.
     * @param address the address for which we need information
     * @return fire station number attached to the given address and list of the persons
     * living at this address including phone, age, medical record
     */
    @GetMapping("/fire")
    public List<FireDTO> getFirePersons(@RequestParam("address") String address) {
        if (address.equals("")) {
            LOGGER.error("URL /fire Request: a parameter \"address\" is needed");
            return null;
        } else {
            LOGGER.info("URL /fire: request sent for address " + address);
            return fireService.getFirePersonList(address);
        }
    }



}
