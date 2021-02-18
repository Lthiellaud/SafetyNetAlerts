package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.FirePersonDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        public List<FirePersonDTO> getFirePersonList(String address) {
            List<FirePersonDTO> firePersonDTOS = new ArrayList<>();
            List<Integer> stations = fireStationService.getStations(address);
            List<PersonPhoneMedicalRecordDTO> persons = alertListsService
                    .getPersonPhoneMedicalRecordDTO(address);
            if (stations.size() != 0 || persons.size() != 0) {
                firePersonDTOS.add(new FirePersonDTO(stations, persons));
            }
            return firePersonDTOS;
        }


    }
