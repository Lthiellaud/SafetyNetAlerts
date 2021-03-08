package com.safetynet.safetynetalerts.service.implementation;

import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.service.AlertListsService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlertListsServiceImpl implements AlertListsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertListsServiceImpl.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PersonService personService;

    private static PersonId personId;
    private static final DateUtil DATE_UTIL = new DateUtil();

    @Override
    public List<PersonPhoneMedicalRecordDTO> getPersonPhoneMedicalRecordDTO(String address) {
        //Retrieve list of persons at the given address using PersonMedicalRecordDTO model
        List<PersonMedicalRecordDTO> persons = getMedicalRecordByAddress(address, false);
        List<PersonPhoneMedicalRecordDTO> personList = new ArrayList<>();

        //Setting the list items to PersonPhoneMedicalRecordDTO model
        if (persons.size() > 0) {
            persons.forEach(person -> personList.add(new PersonPhoneMedicalRecordDTO(person)));
            LOGGER.debug("getPersonPhoneMedicalRecordDTO: " + persons.size() + " persons found " +
                    "for address " + address);
        } else {
            LOGGER.debug("getPersonPhoneMedicalRecordDTO:  nobody found " +
                    "for address " + address);
        }
        return personList;
    }

    @Override
    public void getMedicalRecord(List<PersonMedicalRecordDTO> persons, boolean ageOnly) {
        persons.forEach(person -> {
            personId = new PersonId(person.getFirstName(), person.getLastName());
            Optional<MedicalRecord> m = medicalRecordService.getMedicalRecord(personId);
            if (m.isPresent()) {
                MedicalRecord medicalRecord = m.get();
                person.setAge(DATE_UTIL.age(medicalRecord.getBirthdate()));
                LOGGER.debug("getMedicalRecord: age added for " + personId.toString());
                if (!ageOnly) {
                    person.setMedications(medicalRecord.getMedications());
                    person.setAllergies(medicalRecord.getAllergies());
                    LOGGER.debug("getMedicalRecord: medications and allergies added for "
                            + personId.toString());
                }
            }
        });
    }

    @Override
    public List<PersonMedicalRecordDTO> getMedicalRecordByAddress(String address, boolean ageOnly) {
        List<PersonMedicalRecordDTO> persons =
                personService.getAllByAddress(address);
        getMedicalRecord(persons, ageOnly);
        LOGGER.debug("getMedicalRecordByAddress: response sent for address " + address
                + " and ageOnly " + ageOnly);
        return persons;
    }

}
