package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.PersonAndCountByFireStationDTO;
import com.safetynet.safetynetalerts.service.PersonAndCountByFireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
public class PersonAndCountByFireStationController {

    @Autowired
    private PersonAndCountByFireStationService personAndCountByFireStationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonAndCountByFireStationController.class);

    /**
     * URL /firestation?stationNumber=<station_number>
     * @param station The station number for which we need the list
     * @return the list to be found
     */
    @GetMapping("/firestation")
    public List<PersonAndCountByFireStationDTO> getFireStationPerson(@RequestParam("stationNumber") Integer station) {
        LOGGER.info("URL /firestation: request sent for fire station " + station);
        return personAndCountByFireStationService.getPersonAndCountByFireStation(station);
    }
    
}
