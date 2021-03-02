package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        LOGGER.info("Endpoint /medicalRecord: Creation of medical record for " +
                medicalRecord.getFirstName() + " " + medicalRecord.getLastName() + " asked");
        return medicalRecordService.createMedicalRecord(medicalRecord);

    }

    /**
     * To delete a medical record.
     * @param firstName the firstname of the person whose medical record must be deleted
     * @param lastName the lastname of the person whose medical record must be deleted
     */

    @DeleteMapping("/medicalRecord/{firstName}:{lastName}")
    public void deleteMedicalRecord(@PathVariable("firstName") String firstName,
                                    @PathVariable("lastName") String lastName) {
        LOGGER.info("Endpoint /medicalRecord/{firstName}:{lastName}: Deletion of medical record for " +
                firstName + " " + lastName + " asked");
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }

    /**
     * To update a medical record.
     * @return the updated medical record
     */
    @PutMapping("/medicalRecord/")
    public Optional<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord  ) {
        LOGGER.info("Endpoint /medicalRecord: update asked for medicale record of " +
                medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }
}
