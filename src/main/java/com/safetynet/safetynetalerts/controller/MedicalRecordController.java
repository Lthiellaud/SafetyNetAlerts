package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

/**
 * Defines the endpoint /medicalrecord.
 * Implemented actions : Post/Put/Delete
 */
@RestController
public class MedicalRecordController {

    private static Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * To add a new medical record.
     * @param medicalRecord the medical record to be added
     * @return the added medical record
     */
    @PostMapping(value="/medicalRecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        PersonId personId = new PersonId(medicalRecord.getFirstName(), medicalRecord.getLastName());
        logger.info("medical record of " + personId.toString() + " saved");
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    /**
     * To delete a medical record.
     * @param firstName the firstname of the person whose medical record must be deleted
     * @param lastName the lastname of the person whose medical record must be deleted
     */
    @DeleteMapping(value = "/medicalRecord/{firstName}:{lastName}")
    public void deleteMedicalRecord(@PathVariable("firstName") String firstName,
                                    @PathVariable("lastName") String lastName) {
        PersonId personId = new PersonId(firstName, lastName);
        Optional<MedicalRecord> m = medicalRecordService.getMedicalRecord(personId);
        if (m.isPresent()) {
            logger.info("medical record of " + personId.toString() + " deleted");
            medicalRecordService.deleteMedicalRecord(personId);
        } else {
            logger.error("The medical record of " + personId.toString() + " does not exists");
        }
    }

    /**
     * To update a medical record.
     * @return the updated medical record
     */
    @PutMapping(value="/medicalRecord/")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord  ) {
        PersonId personId = new PersonId(medicalRecord.getFirstName(), medicalRecord.getLastName());
        Optional<MedicalRecord> m = medicalRecordService.getMedicalRecord(personId);
        if (m.isPresent()) {
            MedicalRecord currentMedicalRecord = m.get();

            Date birthdate = medicalRecord.getBirthdate();
            if (birthdate != null) {
                currentMedicalRecord.setBirthdate(birthdate);
            }
            currentMedicalRecord.setMedications(medicalRecord.getMedications());
            currentMedicalRecord.setAllergies(medicalRecord.getAllergies());
            medicalRecordService.saveMedicalRecord(currentMedicalRecord);
            logger.info("medical record og " + personId.toString() + " updated");
            return currentMedicalRecord;
        } else {
            logger.error("The medical record of " + personId.toString() + " does not exists");
            return null;
        }
    }
}
