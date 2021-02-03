package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class FireStationController {
    
    @Autowired
    private FireStationService fireStationService;
    
    @PostMapping(value="/firestation")
    public FireStation createFireStation (@RequestBody FireStation fireStation) {
        return fireStationService.saveFireStation(fireStation);
    }
    
    @PutMapping(value="/firestation/{address}")
    public FireStation updateFireStation (@PathVariable("address") String address, @RequestBody FireStation fireStation) {
        Optional<FireStation> f = fireStationService.getFireStation(address);
        if (f.isPresent()) {
            FireStation currentFireStation = f.get();
            Integer station = fireStation.getStation();
            if (station != null) {
                currentFireStation.setStation(station);
            }
            fireStationService.saveFireStation(currentFireStation);
            return currentFireStation;
        } else {
            return null;
        }
    }

    @DeleteMapping(value="/firestation/address={address}")
    public void deleteFireStationById (@PathVariable("address") String address) {
        fireStationService.deleteFireStation(address);
    }

    @DeleteMapping(value="/firestation/station={station}")
    public void deleteFireStation (@PathVariable("station") Integer station) {
        fireStationService.deleteFireStation(station);
    }
}
