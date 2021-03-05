package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.PersonAndCountByFireStationDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonByFireStationDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonAndCountByFireStationService {
    @Autowired
    private AlertListsService alertListsService;

    @Autowired
    private FireStationService fireStationService;

    private final Logger LOGGER = LoggerFactory.getLogger(PersonAndCountByFireStationService.class);

    /**
     * To get the list of the inhabitants to a given fire station, giving children and adults count.
     * @param stationNumber The station number for which we need the list
     * @return the list to be found
     */
    public List<PersonAndCountByFireStationDTO> getPersonAndCountByFireStation(int stationNumber) {
        PersonAndCountByFireStationDTO personAndCountByFireStation =
                new PersonAndCountByFireStationDTO();
        List<PersonMedicalRecordDTO> personsWithAge = new ArrayList<>();
        List<PersonByFireStationDTO> personByFireStationList = new ArrayList<>();
        List<PersonAndCountByFireStationDTO> resultList = new ArrayList<>();

        //address list for fire station number "stationNumber"
        List<String> addresses = fireStationService.getAddresses(stationNumber);

        //list of the persons who live at these addresses with their age
        if (addresses.size() > 0) {
            addresses.forEach(address ->
                personsWithAge.addAll(alertListsService
                        .getMedicalRecordByAddress(address, true))
            );
        }

        //Constitution of the output record
        int i = personsWithAge.size();
        if (i > 0) {
            personAndCountByFireStation.setChildrenCount(personsWithAge
                    .stream()
                    .filter(p -> p.getAge() < 19)
                    .count());
            personAndCountByFireStation.setAdultsCount(personsWithAge
                    .stream()
                    .filter(p -> p.getAge() > 18)
                    .count());
            //Fill PersonByFireStationDTO data from PersonMedicalRecordDTO and add it to
            // personByFireStationList
            personsWithAge.forEach(p -> personByFireStationList.add(new PersonByFireStationDTO(p)));
            //complete PersonAndCountByFireStationDTO with personByFireStationList
            personAndCountByFireStation.setPersonsByFireStation(personByFireStationList);
            resultList.add(personAndCountByFireStation);
            LOGGER.debug("getPersonAndCountByFireStation: " + i + " persons found for fire " +
                    "station number " + stationNumber);
        } else {
            LOGGER.debug("getPersonAndCountByFireStation: nobody found for fire station"
                    + " number " + stationNumber);
        }

        return resultList;
    }
}
