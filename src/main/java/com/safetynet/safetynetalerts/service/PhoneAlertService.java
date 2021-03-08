package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.IPhoneAlertDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneAlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneAlertService.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;


    /**
     * To get the phone list of the inhabitants attached to a given fire station.
     * @param station the station number of the fire station
     * @return the phone list of the inhabitants attached to a given fire station
     */
    public List<IPhoneAlertDTO> getPhones(Integer station) {
        List<String> addresses = fireStationService.getAddresses(station);
        List<IPhoneAlertDTO> persons = new ArrayList<>();
        if (addresses.size() > 0) {
            persons = personService.getPhones(addresses);
        }
        int i = persons.size();
        if (i > 0) {
            LOGGER.debug("getPhones: " + i + " found for stations number " + station);
        } else {
            LOGGER.debug("getPhones: nobody found for stations number " + station);
        }
        return persons;
    }


}
