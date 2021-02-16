package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.IPersonPhoneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationListsService {

    @Autowired
    FireStationService fireStationService;
    @Autowired
    PersonService personService;


    public List<IPersonPhoneDTO> getPhones(Integer station) {
        List<String> addresses = fireStationService.getAddresses(station);

        return personService.getPhones(addresses);
    }

}
