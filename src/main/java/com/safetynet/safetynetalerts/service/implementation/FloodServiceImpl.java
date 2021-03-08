package com.safetynet.safetynetalerts.service.implementation;

import com.safetynet.safetynetalerts.model.DTO.FloodDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonByAddressDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
import com.safetynet.safetynetalerts.service.AlertListsService;
import com.safetynet.safetynetalerts.service.FireStationService;
import com.safetynet.safetynetalerts.service.FloodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FloodServiceImpl implements FloodService {
    @Autowired
    private AlertListsService alertListsService;

    @Autowired
    private FireStationService fireStationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FloodServiceImpl.class);

    @Override
    public List<FloodDTO> getFloodList(List<Integer> stations) {
        List<FloodDTO> floodList = new ArrayList<>();
        int nbPerson = 0;
        for (Integer station : stations) {
            List<PersonByAddressDTO> personByAddressList = new ArrayList<>();
            List<String> addresses = fireStationService.getAddresses(station);
            //retrieve the list of persons (PersonPhoneMedicalRecordDTO model) for each address
            if (addresses.size() > 0) {
                addresses.forEach(address -> {
                    List<PersonPhoneMedicalRecordDTO> persons =
                            alertListsService.getPersonPhoneMedicalRecordDTO(address);
                    if (persons.size() > 0 ) {
                        PersonByAddressDTO p = new PersonByAddressDTO();
                        p.setAddress(address);
                        p.setPersons(persons);
                        personByAddressList.add(p);
                    }
                });
                nbPerson += personByAddressList.size();
            }
            //for each station, add the list of persons by address
            LOGGER.debug("getFloodList: " + nbPerson + " persons found for" +
                    " station " + station);
            if (nbPerson > 0) {
                floodList.add(new FloodDTO(station, personByAddressList));
            }
        }
        return floodList;
    }



}
