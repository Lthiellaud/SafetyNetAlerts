package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.controller.PersonController;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

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
        if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null) {
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
                logger.info("updateMedicalRecord: record for " + personId.toString() + " updated");
                return Optional.of(currentMedicalRecord);
            } else {
                logger.error("updateMedicalRecord: no record for " + personId.toString() + " founded");
                return m;
            }
        } else {
            logger.error("medical record update impossible : firstname and lastname mandatory");
            return Optional.empty();
        }
    }

    /**
     * To add a new medical record.
     * @param medicalRecord the medical record to be added
     * @return the added medical record
     */
    public Optional<MedicalRecord> createMedicalRecord(MedicalRecord medicalRecord) {
        if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null) {
            PersonId personId = new PersonId(medicalRecord.getFirstName(), medicalRecord.getLastName());
            boolean existing = getMedicalRecord(personId).isPresent();
            if (!existing) {
                medicalRecordRepository.save(medicalRecord);
                logger.info("createMedicalRecord: record for " + personId.toString() + " created");
                return Optional.of(medicalRecord);
            } else {
                logger.info("createMedicalRecord: record for " + personId.toString() + " already exists. " +
                        "Nothing done");
                return Optional.empty();
            }
        } else {
            logger.error("person creation impossible : firstname and lastname mandatory");
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
    public void deleteMedicalRecord(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            PersonId personId = new PersonId(firstName, lastName);
            Optional<MedicalRecord> m = getMedicalRecord(personId);
            if (m.isPresent()) {
                logger.info("deleteMedicalRecord: medical record of " + personId.toString() + " deleted");
                medicalRecordRepository.deleteById(personId);
            } else {
                logger.error("deleteMedicalRecord: The medical record of " + personId.toString() + " does not exists");
            }
        } else {
            logger.error("deleteMedicalRecord: first name ans last name are mandatory");
        }
    }

    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }


}
