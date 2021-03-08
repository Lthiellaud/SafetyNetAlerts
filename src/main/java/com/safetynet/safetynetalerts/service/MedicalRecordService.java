package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;

import java.util.List;
import java.util.Optional;

/**
 * Service used by MedicalRecord Controller
 */
public interface MedicalRecordService {
    /**
     * To get a medical record from a firstname and a lastname.
     * @param personId defines firstname and lastname of the medical record to be gotten
     * @return the founded medical record
     */
    Optional<MedicalRecord> getMedicalRecord(PersonId personId);

    /**
     * To apply the modifications to a medical record. Except for medications and allergies,
     * only non null attributes will be changed.
     * @param medicalRecord the modified medical record to be used for updating th medical record
     * @return the updated medical record or an empty optional if nothing done
     */
    Optional<MedicalRecord> updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * To add a new medical record.
     * @param medicalRecord the medical record to be added
     * @return the added medical record  or an empty optional if nothing done
     */
    Optional<MedicalRecord> createMedicalRecord(MedicalRecord medicalRecord);

    /**
     * To add a list of new medical records.
     * @param medicalRecords the list of medical records to be added
     * @return the added medical records
     */
    Iterable<MedicalRecord> saveMedicalRecordList(List<MedicalRecord> medicalRecords);

    /**
     * To delete a medical record from a firstname and a lastname.
     * @param firstName defines the firstname of the medical record to be deleted
     * @param lastName defines the lastname of the medical record to be deleted
     * @return true if a record has been deleted, false otherwise
     */
    boolean deleteMedicalRecord(String firstName, String lastName);
}
