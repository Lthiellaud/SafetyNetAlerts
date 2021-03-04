package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    private static Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);

    /**
     * To get a medical record from a firstname and a lastname.
     * @param personId defines firstname and lastname of the medical record to be gotten
     * @return the founded medical record
     */
    public Optional<MedicalRecord> getMedicalRecord(PersonId personId){
        return medicalRecordRepository.findById(personId);
    }

    /**
     * To apply the modifications to a medical record. Except for medications and allergies,
     * only non null attributes will be changed.
     * @param medicalRecord the modified medical record to be used for updating th medical record
     * @return the founded medical record
     */
    public Optional<MedicalRecord> updateMedicalRecord(MedicalRecord medicalRecord){

        PersonId personId = new PersonId(medicalRecord.getFirstName(), medicalRecord.getLastName());
        Optional<MedicalRecord> m = getMedicalRecord(personId);
        if (m.isPresent()) {
            MedicalRecord currentMedicalRecord = m.get();
            Date birthdate = medicalRecord.getBirthdate();
            if (birthdate != null) {
                currentMedicalRecord.setBirthdate(birthdate);
            }
            currentMedicalRecord.setMedications(medicalRecord.getMedications());
            currentMedicalRecord.setAllergies(medicalRecord.getAllergies());
            medicalRecordRepository.save(medicalRecord);
            logger.debug("updateMedicalRecord: record for " + personId.toString() + " updated");
            return Optional.of(currentMedicalRecord);
        } else {
            logger.debug("updateMedicalRecord: no record for " + personId.toString() + " founded");
            return m;
        }

    }

    /**
     * To add a new medical record.
     * @param medicalRecord the medical record to be added
     * @return the added medical record
     */
    public Optional<MedicalRecord> createMedicalRecord(MedicalRecord medicalRecord) {

        PersonId personId = new PersonId(medicalRecord.getFirstName(), medicalRecord.getLastName());
        boolean existing = getMedicalRecord(personId).isPresent();
        if (!existing) {
            medicalRecordRepository.save(medicalRecord);
            logger.debug("createMedicalRecord: record for " + personId.toString() + " created");
            return Optional.of(medicalRecord);
        } else {
            logger.debug("createMedicalRecord: record for " + personId.toString() + " already exists. " +
                    "Nothing done");
            return Optional.empty();
        }


    }

    /**
     * To add a list of new medical records.
     * @param medicalRecords the list of medical records to be added
     * @return the added medical records
     */
    public Iterable<MedicalRecord> saveMedicalRecordList(List<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }

    /**
     * To delete a medical record from a firstname and a lastname.
     * @param firstName defines the firstname of the medical record to be deleted
     * @param lastName defines the lastname of the medical record to be deleted
     *
     */
    public boolean deleteMedicalRecord(String firstName, String lastName) {

        PersonId personId = new PersonId(firstName, lastName);
        Optional<MedicalRecord> m = getMedicalRecord(personId);
        if (m.isPresent()) {
            logger.debug("deleteMedicalRecord: medical record of " + personId.toString() + " deleted");
            medicalRecordRepository.deleteById(personId);
            return  true;
        } else {
            logger.debug("deleteMedicalRecord: The medical record of " + personId.toString() + " does not exists");
            return false;
        }

    }

}
