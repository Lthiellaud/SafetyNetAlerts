package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Defines the endpoint /firestation.
 * Implemented actions : Post/Put/Delete
 */
@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FireStationController.class);

    /**
     * POST - To add a new fire station.
     * @param fireStation the fire station to be added
     * @return the added fire station station
     */
    @PostMapping(value="/firestation")
    public Optional<FireStation> createFireStation (@RequestBody FireStation fireStation) {
        LOGGER.info("Endpoint /firestation: Creation of record " + fireStation.toString() + " asked");
        return fireStationService.createFireStation(fireStation);
    }

    /**
     * PUT - To update a station number assigned to an address.
     * @param address The address to be updated
     * @param station the new fire station number
     * @return the updated fire station
     */
    @PutMapping(value="/firestation/{address}")
    @ResponseBody
    public Iterable<FireStation> updateFireStation (@PathVariable("address") String address, @RequestBody Integer station) {
        LOGGER.info("Updating asked for fire station assigned to address : " + address);
        return fireStationService.updateFireStation(address, station);
    }

    /**
     * DELETE - To delete the mapping at an address.
     * @param address the address to be remove from the entity
     */
    @DeleteMapping(value="/firestation/address={address}")
    public void deleteFireStationByAddress (@PathVariable("address") String address) {
        LOGGER.info("Endpoint /firestation/address={address}: deletion asked for records " +
                "FireStation for the address " + address);
        fireStationService.deleteFireStation(address);
    }

    /**
     * DELETE - To delete the mapping of a station.
     * @param station the station to be remove
     */
    @DeleteMapping(value="/firestation/station={station}")
    public void deleteFireStation (@PathVariable("station") Integer station) {
        LOGGER.info("Endpoint /firestation/station={station}: deletion of the records for " +
                "fire station number " + station + " asked");
        fireStationService.deleteFireStation(station);
    }
}
