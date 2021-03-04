package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonInfoService.class);
    @Autowired
    private AlertListsService alertListsService;

    @Autowired
    private PersonService personService;

    /**
     * To get a complete PersonMedicalRecordDTO list of a person.
     * @param firstName the first name of the person for which the PersonMedicalRecordDTO is needed
     * @param lastName the last name of the person for which the PersonMedicalRecordDTO is needed
     * @return the list of PersonMedicalRecordDTO
     */
    public List<PersonMedicalRecordDTO> getPersonInfo(String firstName, String lastName) {
        List<PersonMedicalRecordDTO> persons =
                personService.getAllByFirstAndLastName(firstName, lastName);
        LOGGER.debug("getPersonInfo: " + persons.size() + " for " + firstName + " " + lastName);
        alertListsService.getMedicalRecord(persons, false);
        LOGGER.debug("getPersonInfo: medical records completed for " + firstName + " " + lastName);
        return persons;
    }
}
