package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * @return the added fire station station and http status of the request
     */
    @PostMapping(value = "/firestation")
    public ResponseEntity<FireStation> createFireStation(@RequestBody FireStation fireStation) {
        if (!fireStation.getAddress().equals("") && fireStation.getId() == null) {
            LOGGER.info("Endpoint /firestation: Creation of record "
                    + fireStation.toString() + " asked");
            Optional<FireStation> fireStation1 = fireStationService.createFireStation(fireStation);
            if (fireStation1.isPresent()) {
                LOGGER.info("Endpoint /firestation: Creation done");
                return new ResponseEntity<>(fireStation1.get(), HttpStatus.CREATED);
            } else {
                LOGGER.info("Endpoint /firestation creation request: fire station already exist");
                return  new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } else {
            LOGGER.error("Endpoint /firestation creation request: Address" +
                    " and Station mandatory, Id forbidden");
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * PUT - To update a station number assigned to an address.
     * @param address The address to be updated
     * @param station the new fire station number
     * @return the updated fire station and http status of the request
     */
    @PutMapping(value = "/firestation/{address}")
    @ResponseBody
    public ResponseEntity<List<FireStation>>
                updateFireStation(@PathVariable("address") String address,
                                  @RequestBody Integer station) {

        LOGGER.info("Updating asked for fire station assigned to address : " + address);
        List<FireStation> fireStations = fireStationService.updateFireStation(address, station);
        int i = fireStations.size();
        if (i > 0) {
            LOGGER.info("Endpoint /firestation update request: " + i + " fire station updated");
            return new ResponseEntity<>(fireStations, HttpStatus.OK);
        } else {
            LOGGER.info("Endpoint /firestation update request: fire station not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE - To delete the mapping at an address.
     * @param address the address to be remove from the entity
     * @return http status of the request
     */
    @DeleteMapping(value = "/firestation/address={address}")
    public ResponseEntity<?> deleteFireStationByAddress(@PathVariable("address") String address) {
        if (address != null && !address.equals("")) {
            LOGGER.info("Endpoint /firestation/address={address}: deletion asked for records " +
                    "FireStation for the address " + address);
            int nbFireStationFromAddress = fireStationService.deleteFireStationByAddress(address);
            if (nbFireStationFromAddress == 0) {
                LOGGER.info("Endpoint /firestation/address={address} delete request:" +
                        " no fire station found for the address");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                LOGGER.info("Endpoint /firestation/address={address} :" + nbFireStationFromAddress +
                        " record deleted for the address");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            LOGGER.error("Endpoint /firestation/address={address} delete request:" +
                    " address is mandatory");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE - To delete the mapping of a station.
     * @param station the station to be remove
     * @return http status of the request
     */
    @DeleteMapping(value = "/firestation/station={station}")
    public ResponseEntity<?> deleteFireStation(@PathVariable("station") Integer station) {
        LOGGER.info("Endpoint /firestation/station={station}: deletion of the records for " +
                "fire station number " + station + " asked");
        int i = fireStationService.deleteFireStationByStation(station);
        if (i == 0) {
            LOGGER.info("Endpoint /firestation/station={station} delete request:" +
                    " no address attached to this station");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            LOGGER.info("Endpoint /firestation/station={station} delete request:" + i +
                    " record deleted for the station");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
