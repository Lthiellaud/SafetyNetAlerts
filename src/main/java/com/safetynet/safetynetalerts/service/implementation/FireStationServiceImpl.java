package com.safetynet.safetynetalerts.service.implementation;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationServiceImpl implements FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FireStationServiceImpl.class);

    @Override
    public Optional<FireStation> createFireStation(FireStation fireStation) {
        List<FireStation> fireStations = fireStationRepository
                .findAllByAddressAndStation(fireStation.getAddress(), fireStation.getStation());
        if (fireStations.size() > 0) {
            LOGGER.debug("createFireStation: fire station already assigned to this address");
            return Optional.empty();
        } else {
            fireStation = fireStationRepository.save(fireStation);
            LOGGER.debug("createFireStation: Fire station " + fireStation.toString() + " added");
            return Optional.of(fireStation);
        }
    }

    @Override
    public List<FireStation> updateFireStation(String address, Integer station) {
        List<FireStation> fireStations = fireStationRepository.findByAddress(address);
        List<FireStation> fireStationsUpdated = new ArrayList<>();
        for (FireStation f : fireStations) {
            f.setStation(station);
            fireStationsUpdated.add(f);
            fireStationRepository.save(f);
        }
        int i = fireStationsUpdated.size();
        if (i > 0) {
            LOGGER.debug("updateFireStation: " + i +
                    " record(s) fire station updated for the address : " + address);
        } else {
            LOGGER.debug("updateFireStation: no fire station for the address" + address);
        }

        return fireStationsUpdated;
    }

    @Override
    public Iterable<FireStation> saveFireStationList(List<FireStation> fireStations) {
        return fireStationRepository.saveAll(fireStations);
    }

    @Override
    public Integer deleteFireStationByAddress(String address) {
        List<FireStation> fireStations = fireStationRepository.findByAddress(address);
        int i = fireStations.size();
        if (i > 0) {
            LOGGER.debug("deleteFireStation(\"" + address + "\"): " + i +
                    " fire stations deleted");
            fireStationRepository.deleteAll(fireStations);
        } else {
            LOGGER.debug("deleteFireStation(\"" + address + "\"): no fire station deleted");
        }
        return i;
    }

    @Override
    public Integer deleteFireStationByStation(Integer station) {
        List<FireStation> fireStations = fireStationRepository.findByStation(station);
        int i = fireStations.size();
        if (i > 0) {
            LOGGER.debug("deleteFireStation(" + station + "): " + i +
                    " fire stations deleted");
            fireStationRepository.deleteAll(fireStations);
        } else {
            LOGGER.debug("deleteFireStation(" + station + "): no fire station deleted");
        }
        return i;
    }

    @Override
    public List<Integer> getStations(String address) {
        List<FireStation> fireStations = fireStationRepository.findByAddress(address);
        List<Integer> stations = new ArrayList<>();
        int i = fireStations.size();
        if (i > 0) {
            LOGGER.debug("getStations(\"" + address + "\"): " + i + " fire station found");
            stations = fireStations.stream().map(FireStation::getStation)
                    .collect(Collectors.toList());
        } else {
            LOGGER.debug("getStations(\"" + address + "\"): No fire station found");
        }
        return stations;
    }

    @Override
    public List<String> getAddresses(Integer station) {
        List<FireStation> fireStations = fireStationRepository.findByStation(station);
        List<String> addresses = new ArrayList<>();
        int i = fireStations.size();
        if (i > 0) {
            LOGGER.debug("getAddresses: " + i + " addresses found for fire station " + station);
            addresses = fireStations.stream().map(FireStation::getAddress)
                    .distinct().collect(Collectors.toList());
        } else {
            LOGGER.debug("getAddresses:  no address attached to this fire station " + station);
        }
        return addresses;
    }
}
