package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.*;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlertListsService {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    private static PersonId personId;
    private static final DateUtil DATE_UTIL = new DateUtil();

    /**
     * To get the list of the inhabitants living at an address.
     * @param address the address for which we need the inhabitants list
     * @return the list of inhabitants at the given address including phone and medical record
     */
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

    /**
     * To complete PersonMedicalRecordDTO with medical record information (ageOnly false) and age.
     * @param persons the list of PersonMedicalRecordDTO to be completed
     * @param ageOnly true if only age is needed, false if allergies and medications are also needed
     */
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

    /**
     * To complete PersonMedicalRecordDTO list of the persons living at a given address.
     * Data added: age or age, medications and allergies.
     * @param address the address for which the PersonMedicalRecordDTO are needed
     * @param ageOnly true if only age is needed, false if allergies and medications are also needed
     * @return the list of PersonMedicalRecordDTO
     */
    public List<PersonMedicalRecordDTO> getMedicalRecordByAddress(String address, boolean ageOnly) {
        List<PersonMedicalRecordDTO> persons =
                personService.getAllByAddress(address);
        getMedicalRecord(persons, ageOnly);
        return persons;
    }

}
