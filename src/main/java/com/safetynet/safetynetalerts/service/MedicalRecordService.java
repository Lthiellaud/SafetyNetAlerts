package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import lombok.Data;
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
        Optional<MedicalRecord> m = medicalRecordRepository.findById(personId);
        if (m.isPresent()) {
            MedicalRecord currentMedicalRecord = m.get();

            Date birthdate = medicalRecord.getBirthdate();
            if (birthdate != null) {
                currentMedicalRecord.setBirthdate(birthdate);
            }
            currentMedicalRecord.setMedications(medicalRecord.getMedications());
            currentMedicalRecord.setAllergies(medicalRecord.getAllergies());
            return Optional.of(currentMedicalRecord);
        } else {
            return m;
        }
    }

    /**
     * To add a new medical record.
     * @param medicalRecord the medical record to be added
     * @return the added medical record
     */
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
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
     * @param personId defines firstname and lastname of the medical record to be deleted
     */
    public void deleteMedicalRecord(PersonId personId) {
        medicalRecordRepository.deleteById(personId);
    }

    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }
    public Optional<MedicalRecord> getPersonMedicalRecord(PersonId personId) {
        return  medicalRecordRepository.findById(personId);
    }


}
