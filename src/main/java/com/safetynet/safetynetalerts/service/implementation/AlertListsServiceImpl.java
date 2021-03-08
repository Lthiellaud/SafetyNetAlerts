package com.safetynet.safetynetalerts.service.implementation;

import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.service.AlertListsService;
import com.safetynet.safetynetalerts.service.FireStationService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlertListsServiceImpl implements AlertListsService {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

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
                if (!ageOnly) {
                    person.setMedications(medicalRecord.getMedications());
                    person.setAllergies(medicalRecord.getAllergies());
                }
            }
        });
    }

    @Override
    public List<PersonMedicalRecordDTO> getMedicalRecordByAddress(String address, boolean ageOnly) {
        List<PersonMedicalRecordDTO> persons =
                personService.getAllByAddress(address);
        getMedicalRecord(persons, ageOnly);
        return persons;
    }

}
