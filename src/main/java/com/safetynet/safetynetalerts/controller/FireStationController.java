package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Defines the endpoint /firestation.
 * Implemented actions : Post/Put/Delete
 */
@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    private static Logger logger = LoggerFactory.getLogger(FireStationController.class);

    /**
     * POST - To add a new fire station.
     * @param fireStation the fire station to be added
     * @return the added fire station station
     */
    @PostMapping(value="/firestation")
    public FireStation createFireStation (@RequestBody FireStation fireStation) {

        logger.info("Fire station " + fireStation.toString() + " added");
        return fireStationService.saveFireStation(fireStation);
    }

    /**
     * PUT - To update a station number assigned to an address.
     * @param address The address to be updated
     * @param station the new fire station number
     * @return the updated fire station
     */
    @PutMapping(value="/firestation/{address}")
    public Iterable<FireStation> updateFireStation (@PathVariable("address") String address, @RequestBody Integer station) {
        logger.info("Updating asked for fire station assigned to address : " + address);
        Iterable<FireStation> fireStations = fireStationService.getFireStation(address);
        int i = 0;
        if (station != null) {
            for (FireStation currentFireStation:fireStations) {
                currentFireStation.setStation(station);
                fireStationService.saveFireStation(currentFireStation);
                i++;
            }
        }
        if (i>0) {
            logger.info(i + " record(s) fire station updated for the address : " + address);
        } else {
            logger.info("no fire station for this address");
        }
        return fireStations;
    }

    /**
     * DELETE - To delete the mapping at an address.
     * @param address the address to be remove from the entity
     */
    @DeleteMapping(value="/firestation/address={address}")
    public void deleteFireStationByAddress (@PathVariable("address") String address) {
        logger.info("records fire station for the address " + address + " deleted");
        fireStationService.deleteFireStation(address);
    }

    /**
     * DELETE - To delete the mapping of a station.
     * @param station the station to be remove
     */
    @DeleteMapping(value="/firestation/station={station}")
    public void deleteFireStation (@PathVariable("station") Integer station) {
        logger.info("records for fire station number " + station + " deleted");
        fireStationService.deleteFireStation(station);
    }
}
