package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.DTO.IPersonPhoneDTO;
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


    public List<IPersonPhoneDTO> getPhones(Integer firestation) {
        List<String> addresses = getAddresses(firestation);

        return personRepository.findAllDistinctPhoneByAddressIsIn(addresses);
    }

    public List<String> getAddresses(Integer firestation) {
        List<FireStation> fireStations = fireStationRepository.findDistinctByStation(firestation);
        List<String> addresses = new ArrayList<>();
        if (fireStations != null) {
            addresses = fireStations.stream().map(FireStation::getAddress)
                    .distinct().collect(Collectors.toList());
        }
        return addresses;
    }


}
