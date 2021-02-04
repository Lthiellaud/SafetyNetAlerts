package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
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

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * To add a new medical record.
     * @param medicalRecord the medical record to be added
     * @return the added medical record
     */
    @PostMapping(value="/medicalRecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
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
        medicalRecordService.deleteMedicalRecord(new PersonId(firstName, lastName));
    }

    /**
     * To update a medical record.
     * @param firstName the firstname of the person whose medical record must be updated
     * @param lastName the lastname of the person whose medical record must be updated
     * @return the updated medical record
     */
    @PutMapping(value="/medicalRecord/{firstName}:{lastName}")
    public MedicalRecord updateMedicalRecord(@PathVariable("firstName") String firstName,
                                             @PathVariable("lastName") String lastName ,
                                             @RequestBody MedicalRecord medicalRecord  ) {
        Optional<MedicalRecord> m = medicalRecordService.getMedicalRecord(new PersonId(firstName, lastName));
        if (m.isPresent()) {
            MedicalRecord currentMedicalRecord = m.get();

            Date birthdate = medicalRecord.getBirthdate();
            if (birthdate != null) {
                currentMedicalRecord.setBirthdate(birthdate);
            }
            currentMedicalRecord.setMedications(medicalRecord.getMedications());
            currentMedicalRecord.setAllergies(medicalRecord.getAllergies());
            medicalRecordService.saveMedicalRecord(currentMedicalRecord);
            return currentMedicalRecord;
        } else {
            return null;
        }
    }
}
