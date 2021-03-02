package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.FloodListByStationDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonByAddressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FloodService {
    @Autowired
    AlertListsService alertListsService;

    @Autowired
    FireStationService fireStationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FloodService.class);

    /**
     * to get the list of the households attached to the given fire stations.
     * @param stations the list of fire stations for which we need the list
     * @return the list of households by address including name, phone, age
     * medical record of each member
     */
    public List<FloodListByStationDTO> getFloodList(List<Integer> stations) {
        List<FloodListByStationDTO> floodList = new ArrayList<>();
        int nbPerson = 0;
        for (Integer station : stations) {
            List<PersonByAddressDTO> personByAddressList = new ArrayList<>();
            List<String> addresses = fireStationService.getAddresses(station);
            //retrieve the list of persons (PersonPhoneMedicalRecordDTO model) for each address
            if (addresses.size() > 0) {
                addresses.forEach(address -> {
                    PersonByAddressDTO p = new PersonByAddressDTO();
                    p.setAddress(address);
                    p.setPersons(alertListsService.getPersonPhoneMedicalRecordDTO(address));
                    personByAddressList.add(p);
                });
                nbPerson += personByAddressList.size();
            }
            //for each station, add the list of persons by address
            LOGGER.info("getFloodList: " + nbPerson + " persons found for" +
                    " station " + station);
            floodList.add(new FloodListByStationDTO(station, personByAddressList));
        }
        return floodList;
    }



}
