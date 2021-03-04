package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * Defines the endpoint /medicalrecord.
 * Implemented actions : Post/Put/Delete
 */
@RestController
public class MedicalRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * To add a new medical record.
     * @param medicalRecord the medical record to be added
     * @return the added medical record
     */
    @PostMapping(value="/medicalRecord")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        if (!medicalRecord.getFirstName().equals("") && !medicalRecord.getLastName().equals("")) {
            LOGGER.info("Endpoint /medicalRecord: creation of Medical Record request for " + medicalRecord.getFirstName() +
                    " " + medicalRecord.getLastName() + " received");
            Optional<MedicalRecord> medicalRecord1 = medicalRecordService.createMedicalRecord(medicalRecord);
            if (medicalRecord1.isPresent()) {
                LOGGER.info("Endpoint /medicalRecord: creation done");
                return new ResponseEntity<>(medicalRecord1.get(), HttpStatus.CREATED);
            } else {
                LOGGER.info("Endpoint /medicalRecord: medicalRecord already existing");
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
        } else {
            LOGGER.error("Endpoint /medicalRecord Create request: firstname and lastname mandatory");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * To delete a medical record.
     * @param firstName the firstname of the person whose medical record must be deleted
     * @param lastName the lastname of the person whose medical record must be deleted
     */

    @DeleteMapping("/medicalRecord/{firstName}:{lastName}")
    public ResponseEntity<?> deleteMedicalRecord(@PathVariable("firstName") String firstName,
                                    @PathVariable("lastName") String lastName) {
        if (!firstName.equals("")  && !lastName.equals("")) {
            LOGGER.info("Endpoint /medicalRecord/{firstName}:{lastName}: Deletion of medical record for " +
                    firstName + " " + lastName + " asked");
            if (medicalRecordService.deleteMedicalRecord(firstName, lastName)) {
                LOGGER.info("Endpoint /medicalRecord: deletion completed");
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } else {
                LOGGER.info("Endpoint /medicalRecord delete request: medical record not found");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            LOGGER.error("Endpoint /medicalRecord delete request: firstname and lastname mandatory");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * To update a medical record.
     * @return the updated medical record
     */
    @PutMapping("/medicalRecord/")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord  ) {
        if (!medicalRecord.getFirstName().equals("") && !medicalRecord.getLastName().equals("")) {
            LOGGER.info("Endpoint /medicalRecord: update asked for medicale record of " +
                    medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
            Optional<MedicalRecord> medicalRecord1 = medicalRecordService.updateMedicalRecord(medicalRecord);
            if (medicalRecord1.isPresent()) {
                LOGGER.info("Endpoint /medicalRecord: update done");
                return new ResponseEntity<>(medicalRecord1.get(), HttpStatus.OK);
            } else {
                LOGGER.info("Endpoint /medicalRecord update request: medical record not found");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            LOGGER.error("Endpoint /medicalRecord update request: firstname and lastname mandatory");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
}
