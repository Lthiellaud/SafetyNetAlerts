package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.FireDTO;

import java.util.List;

public interface FireService {
    /**
         * To get the list of the inhabitants living at an address and the attached fire station.
         * @param address the address for which we need the inhabitants list
         * @return the list of inhabitants at the given address including phone and medical record
         * and the fire stations attached to this address
         */
    List<FireDTO> getFirePersonList(String address);
}
