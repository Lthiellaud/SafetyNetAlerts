package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;

import java.util.List;

/**
 * Services used by several other services.
 */
public interface AlertListsService {
    /**
     * To get the list of the inhabitants living at an address.
     * @param address the address for which we need the inhabitants list
     * @return the list of inhabitants at the given address including phone and medical record
     */
    List<PersonPhoneMedicalRecordDTO> getPersonPhoneMedicalRecordDTO(String address);

    /**
     * To complete PersonMedicalRecordDTO with medical record information (ageOnly false) and age.
     * @param persons the list of PersonMedicalRecordDTO to be completed
     * @param ageOnly true if only age is needed, false if allergies and medications are also needed
     */
    void getMedicalRecord(List<PersonMedicalRecordDTO> persons, boolean ageOnly);

    /**
     * To complete PersonMedicalRecordDTO list of the persons living at a given address.
     * Data added: age or age, medications and allergies.
     * @param address the address for which the PersonMedicalRecordDTO are needed
     * @param ageOnly true if only age is needed, false if allergies and medications are also needed
     * @return the list of PersonMedicalRecordDTO
     */
    List<PersonMedicalRecordDTO> getMedicalRecordByAddress(String address, boolean ageOnly);

}
