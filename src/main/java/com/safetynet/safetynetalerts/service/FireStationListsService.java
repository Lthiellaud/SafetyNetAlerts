package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.IPersonPhone;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FireStationListsService {

    @Autowired
    FireStationRepository fireStationRepository;

    @Autowired
    PersonRepository personRepository;

    /*public Iterable<String> getPhone(Integer firestation) {
        Iterable<String> addresses = getAddresses(firestation);
        List<Person> persons = personRepository.findAllDistinctByAddressIsIn(addresses);
        List<String> phones = new ArrayList<>();
        if (persons != null) {
            phones = persons.stream().map(Person::getPhone).distinct()
                    .collect(Collectors.toList());
        }
        return phones;
    }
*/
    public Iterable<IPersonPhone> getPhones(Integer firestation) {
        Iterable<String> addresses = getAddresses(firestation);

        return personRepository.findAllDistinctPhoneByAddressIsIn(addresses);
    }

    public Iterable<String> getAddresses(Integer firestation) {
        List<FireStation> fireStations = fireStationRepository.findDistinctByStation(firestation);
        List<String> addresses = new ArrayList<>();
        if (fireStations != null) {
            addresses = fireStations.stream().map(FireStation::getAddress)
                    .distinct().collect(Collectors.toList());
        }
        return addresses;
    }


}
