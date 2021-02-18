package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.FirePersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireService {

        @Autowired
        AlertListsService alertListsService;

        @Autowired
        FireStationService fireStationService;


        /**
         * To get the list of the inhabitants living at an address and the attached fire station.
         * @param address the address for which we need the inhabitants list
         * @return the list of inhabitants at the given address including phone and medical record
         * and the fire stations attached to this address
         */
        public FirePersonDTO getFirePersonList(String address) {
           return new FirePersonDTO(fireStationService.getStations(address),
                    alertListsService.getPersonPhoneMedicalRecordDTO(address));
        }


    }
