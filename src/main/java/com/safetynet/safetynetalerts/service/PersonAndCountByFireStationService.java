package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.PersonAndCountByFireStationDTO;

import java.util.List;

public interface PersonAndCountByFireStationService {
    /**
     * To get the list of the inhabitants to a given fire station, giving children and adults count.
     * @param stationNumber The station number for which we need the list
     * @return the list to be found
     */
    List<PersonAndCountByFireStationDTO> getPersonAndCountByFireStation(int stationNumber);
}
