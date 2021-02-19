package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.ChildAlertDTO;
import com.safetynet.safetynetalerts.service.ChildAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RestController
public class ChildAlertController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildAlertController.class);

    @Autowired
    private ChildAlertService childAlertService;
    
    @GetMapping("/childAlert")
    public List<ChildAlertDTO> getChildAlertList(@RequestParam("address") String address ) {
        if (address.equals("")) {
            LOGGER.error("URL /childAlert Request: a parameter \"address\" is needed");
            return null;
        } else {
            LOGGER.info("URL /childAlert: request sent for address " + address);
            return childAlertService.getChildAlertList(address);
        }
    }
    
}
