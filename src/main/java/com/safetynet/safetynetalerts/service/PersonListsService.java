package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.*;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import com.safetynet.safetynetalerts.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonListsService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FireStationRepository fireStationRepository;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    private static PersonId personId;
    private static DateUtil dateUtil;

    /**
     * To get the email list of all the inhabitants of a city.
     * @param city The city for which an email list is needed
     * @return the email list
     */
    public List<IPersonEmail> getEmailList(String city){
        return personRepository.findAllDistinctEmailByCity(city);
    }

    public List<PersonDto> getFirePersonList(String address) {
        List<PersonDto> firePersons = personRepository.findAllByAddress(address);
        dateUtil = new DateUtil();
        firePersons.forEach(firePersonDto -> {
            firePersonDto.setStations(getStations(address));
            personId = new PersonId(firePersonDto.getFirstName(), firePersonDto.getLastName());
            Optional<MedicalRecord> m = getPersonMedicalRecord(personId);
            if (m.isPresent()) {
                MedicalRecord medicalRecord = m.get();
                firePersonDto.setAge(dateUtil.age(medicalRecord.getBirthdate())); //dateUtil.age(medicalRecord.getBirthdate())
                firePersonDto.setMedications(medicalRecord.getMedications());
                firePersonDto.setAllergies(medicalRecord.getAllergies());
            }
        });
        return firePersons;
    }

    private Optional<MedicalRecord> getPersonMedicalRecord(PersonId personId) {
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(personId);
        return  medicalRecord;
    }

    private List<Integer> getStations(String address) {
        List<FireStation> fireStations = fireStationRepository.findAllByAddress(address);
        List<Integer> stations = new ArrayList<>();
        if (fireStations.size() > 0) {
            stations = fireStations.stream().map(FireStation::getStation)
                    .collect(Collectors.toList());
        }
        return stations;
    }
}
