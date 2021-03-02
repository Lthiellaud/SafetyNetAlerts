package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.FloodListByStationDTO;
import com.safetynet.safetynetalerts.model.DTO.IPersonPhoneDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonEmailMedicalRecordDTO;
import com.safetynet.safetynetalerts.service.AlertListsService;
import com.safetynet.safetynetalerts.service.FloodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class FloodController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FloodController.class);

    @Autowired
    private FloodService floodService;

    /**
     * URL /flood/stations?stations=<a list of station_numbers>.
     * @param stations the list of station for which we need the list (integers separated by comma)
     * @return a list of the households attached to the given stations
     */
    @GetMapping("/flood/stations")
    public List<FloodListByStationDTO> getFloodList
            (@RequestParam("stations") List<Integer> stations) {
        if (stations != null && stations.size() > 0) {
            LOGGER.info("URL /flood/stations request: List sent for stations " + stations);
            return floodService.getFloodList(stations);
        } else {
            LOGGER.error("URL /flood/stations request: station list is needed");
            return null;
        }
    }

}
