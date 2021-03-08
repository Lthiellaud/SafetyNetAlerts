package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.ChildAlertDTO;
import com.safetynet.safetynetalerts.service.ChildAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Defines the URL /childAlert{@literal ?}address={address}.
 */
@RestController
public class ChildAlertController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildAlertController.class);

    @Autowired
    private ChildAlertService childAlertService;

    /**
     * URL /childAlert?address={address}.
     * @param address Address for which the child list is required
     * @return the child list with first name, last name, age and a list of other
     *    homehood members
     */
    @GetMapping("/childAlert")
    public ResponseEntity<List<ChildAlertDTO>>
              getChildAlertList(@RequestParam("address") String address) {
        if (address.equals("")) {
            LOGGER.error("URL /childAlert Request: a parameter \"address\" is needed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            LOGGER.info("URL /childAlert: request received for address " + address);
            List<ChildAlertDTO> childAlertList = childAlertService.getChildAlertList(address);
            int i = childAlertList.size();
            if (i > 0) {
                LOGGER.info("URL /childAlert: " + i + " homehood(s) with children for address "
                        + address);
                return new ResponseEntity<>(childAlertList, HttpStatus.OK);
            } else {
                LOGGER.info("URL /childAlert: no child found for address " + address);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }
    }
}
