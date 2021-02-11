package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    @DeleteMapping("/medicalRecord/{firstName}:{lastName}")
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
    @PutMapping("/medicalRecord/")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord  ) {
        PersonId personId = new PersonId(medicalRecord.getFirstName(), medicalRecord.getLastName());
        Optional<MedicalRecord> m = medicalRecordService.updateMedicalRecord(medicalRecord);
        if (m.isPresent()) {
            medicalRecordService.saveMedicalRecord(m.get());
            logger.info("medical record of " + personId.toString() + " updated");
            return m.get();
        } else {
            logger.error("The medical record of " + personId.toString() + " does not exists");
            return null;
        }
    }
    
    @GetMapping("/medicalRecords")
    public Iterable<MedicalRecord> getMedicalRecords(){
        return medicalRecordService.getMedicalRecords();
    }
    
}
