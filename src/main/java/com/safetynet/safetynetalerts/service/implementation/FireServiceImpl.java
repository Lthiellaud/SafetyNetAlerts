package com.safetynet.safetynetalerts.service.implementation;

import com.safetynet.safetynetalerts.model.DTO.FireDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
import com.safetynet.safetynetalerts.service.AlertListsService;
import com.safetynet.safetynetalerts.service.FireService;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireServiceImpl implements FireService {

        @Autowired
        private AlertListsService alertListsService;

        @Autowired
        private FireStationService fireStationService;

        private static final Logger LOGGER = LoggerFactory.getLogger(FireStationService.class);


    @Override
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
