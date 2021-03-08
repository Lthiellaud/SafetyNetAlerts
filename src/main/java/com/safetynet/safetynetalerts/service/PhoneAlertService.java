package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.IPhoneAlertDTO;

import java.util.List;

/**
 * Service used by PhoneAlert Controller
 */
public interface PhoneAlertService {
    /**
     * To get the phone list of the inhabitants attached to a given fire station.
     * @param station the station number of the fire station
     * @return the phone list of the inhabitants attached to a given fire station
     */
    List<IPhoneAlertDTO> getPhones(Integer station);
}
