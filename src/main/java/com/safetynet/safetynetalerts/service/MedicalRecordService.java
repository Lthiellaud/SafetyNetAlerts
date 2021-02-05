package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
