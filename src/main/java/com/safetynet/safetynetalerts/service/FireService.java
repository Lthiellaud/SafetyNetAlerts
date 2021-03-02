package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.FirePersonDTO;
import com.safetynet.safetynetalerts.model.DTO.FloodListByStationDTO;
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
        AlertListsService alertListsService;

        @Autowired
        FireStationService fireStationService;

        private static final Logger LOGGER = LoggerFactory.getLogger(FireStationService.class);


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
            int i = stations.size();
            int j = persons.size();
            if (j != 0) {
                firePersonDTOS.add(new FirePersonDTO(stations, persons));
                LOGGER.info("getFirePersonList: " + i + " fire station(s) and " + j
                        + " persons found for address " + address);
            } else {
                LOGGER.error("getFirePersonList: nobody found for address " + address);
            }
            return firePersonDTOS;
        }


    }
