package com.safetynet.safetynetalerts.service.implementation;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordServiceImpl.class);

    @Override
    public Optional<MedicalRecord> getMedicalRecord(PersonId personId){
        return medicalRecordRepository.findById(personId);
    }

    @Override
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
            LOGGER.debug("updateMedicalRecord: record for " + personId.toString() + " updated");
            return Optional.of(currentMedicalRecord);
        } else {
            LOGGER.debug("updateMedicalRecord: no record for " + personId.toString() + " founded");
            return m;
        }

    }

    @Override
    public Optional<MedicalRecord> createMedicalRecord(MedicalRecord medicalRecord) {

        PersonId personId = new PersonId(medicalRecord.getFirstName(), medicalRecord.getLastName());
        boolean existing = getMedicalRecord(personId).isPresent();
        if (!existing) {
            medicalRecordRepository.save(medicalRecord);
            LOGGER.debug("createMedicalRecord: record for " + personId.toString() + " created");
            return Optional.of(medicalRecord);
        } else {
            LOGGER.debug("createMedicalRecord: record for " + personId.toString() +
                    " already exists. Nothing done");
            return Optional.empty();
        }


    }

    @Override
    public Iterable<MedicalRecord> saveMedicalRecordList(List<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }

    @Override
    public boolean deleteMedicalRecord(String firstName, String lastName) {

        PersonId personId = new PersonId(firstName, lastName);
        Optional<MedicalRecord> m = getMedicalRecord(personId);
        if (m.isPresent()) {
            LOGGER.debug("deleteMedicalRecord: medical record of " + personId.toString() +
                    " deleted");
            medicalRecordRepository.deleteById(personId);
            return  true;
        } else {
            LOGGER.debug("deleteMedicalRecord: The medical record of " + personId.toString() +
                    " does not exists");
            return false;
        }

    }

}
