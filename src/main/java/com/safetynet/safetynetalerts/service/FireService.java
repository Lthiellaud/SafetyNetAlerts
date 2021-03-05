package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.FireDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireService {

        @Autowired
        private AlertListsService alertListsService;

        @Autowired
        private FireStationService fireStationService;

        private static final Logger LOGGER = LoggerFactory.getLogger(FireStationService.class);


    /**
         * To get the list of the inhabitants living at an address and the attached fire station.
         * @param address the address for which we need the inhabitants list
         * @return the list of inhabitants at the given address including phone and medical record
         * and the fire stations attached to this address
         */
        public List<FireDTO> getFirePersonList(String address) {
            List<FireDTO> fireDTOS = new ArrayList<>();
            List<Integer> stations = fireStationService.getStations(address);
            List<PersonPhoneMedicalRecordDTO> persons = alertListsService
                    .getPersonPhoneMedicalRecordDTO(address);
            int i = stations.size();
            int j = persons.size();

            //the list is completed only if somebody is living at the address
            if (j != 0) {
                fireDTOS.add(new FireDTO(stations, persons));
                LOGGER.debug("getFirePersonList: " + i + " fire station(s) and " + j
                        + " persons found for address " + address);
            } else {
                LOGGER.debug("getFirePersonList: nobody found for address " + address);
            }
            return fireDTOS;
        }


    }
