package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
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

    /**
     * POST - To add a new fire station.
     * @param fireStation the fire station to be added
     * @return the added fire station station
     */
    @PostMapping(value="/firestation")
    public FireStation createFireStation (@RequestBody FireStation fireStation) {
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
        Iterable<FireStation> fireStations = fireStationService.getFireStation(address);
        for (FireStation currentFireStation:fireStations) {
            if (station != null) {
                currentFireStation.setStation(station);
            }
            fireStationService.saveFireStation(currentFireStation);
        }
        return fireStations;
    }

    /**
     * DELETE - To delete the mapping at an address.
     * @param address the address to be remove from the entity
     */
    @DeleteMapping(value="/firestation/address={address}")
    public void deleteFireStationById (@PathVariable("address") String address) {
        fireStationService.deleteFireStation(address);
    }

    /**
     * DELETE - To delete the mapping of a station.
     * @param station the station to be remove
     */
    @DeleteMapping(value="/firestation/station={station}")
    public void deleteFireStation (@PathVariable("station") Integer station) {
        fireStationService.deleteFireStation(station);
    }
}
