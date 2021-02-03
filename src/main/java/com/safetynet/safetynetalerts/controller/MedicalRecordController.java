package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping(value="/medicalRecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    @DeleteMapping(value = "/medicalRecord/{firstName}:{lastName}")
    public void deleteMedicalRecord(@PathVariable("firstName") String firstName,
                                    @PathVariable("lastName") String lastName) {
        medicalRecordService.deleteMedicalRecord(new PersonId(firstName, lastName));
    }

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
            List<String> medications = medicalRecord.getMedications();
            if (medications != null) {
                currentMedicalRecord.setMedications(medications);
            }
            List<String> allergies = medicalRecord.getAllergies();
            if (allergies != null) {
                currentMedicalRecord.setAllergies(allergies);
            }
            medicalRecordService.saveMedicalRecord(currentMedicalRecord);
            return currentMedicalRecord;
        } else {
            return null;
        }
    }
}
