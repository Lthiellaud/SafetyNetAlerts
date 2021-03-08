package com.safetynet.safetynetalerts.service.implementation;

import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.service.AlertListsService;
import com.safetynet.safetynetalerts.service.PersonInfoService;
import com.safetynet.safetynetalerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonInfoServiceImpl.class);
    @Autowired
    private AlertListsService alertListsService;

    @Autowired
    private PersonService personService;

    @Override
    public List<PersonMedicalRecordDTO> getPersonInfo(String firstName, String lastName) {
        List<PersonMedicalRecordDTO> persons =
                personService.getAllByFirstAndLastName(firstName, lastName);
        LOGGER.debug("getPersonInfo: " + persons.size() + " for " + firstName + " " + lastName);

        //add medical record information
        alertListsService.getMedicalRecord(persons, false);
        LOGGER.debug("getPersonInfo: medical records completed for " + firstName + " " + lastName);

        return persons;
    }
}
