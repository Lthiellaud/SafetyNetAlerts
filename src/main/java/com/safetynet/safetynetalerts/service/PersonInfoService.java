package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;

import java.util.List;

public interface PersonInfoService {
    /**
     * To get a complete PersonMedicalRecordDTO for a person or a list of person.
     * @param firstName the first name of the person for which the PersonMedicalRecordDTO is needed
     * @param lastName the last name of the person for which the PersonMedicalRecordDTO is needed
     * @return the list of PersonMedicalRecordDTO
     */
    List<PersonMedicalRecordDTO> getPersonInfo(String firstName, String lastName);
}
