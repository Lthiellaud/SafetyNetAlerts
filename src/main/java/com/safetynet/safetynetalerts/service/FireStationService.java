package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FireStationService.class);

    /**
     * To add a new fire station.
     * @param fireStation the fire station to be added
     * @return the added fire station station
     */
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

    /**
     * To update the fire station number for an address.
     * @param address the address to be updated
     * @param station th new fire station number
     * @return the updated fire station
     */
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
            LOGGER.debug("updateFireStation: no fire station for this address");
        }

        return fireStationsUpdated;
    }

    /**
     * To add a list of new fire stations.
     * @param fireStations the fire station to be added
     * @return the added fire station station
     */
    public Iterable<FireStation> saveFireStationList(List<FireStation> fireStations) {
        return fireStationRepository.saveAll(fireStations);
    }

    /**
     * To delete a fire stations from its address.
     * @param address the address of the fire station to be deleted
     */
    public Integer deleteFireStationByAddress(String address) {
        List<FireStation> fireStations = fireStationRepository.findByAddress(address);
        int i = fireStations.size();
        if (i > 0) {
            LOGGER.debug("deleteFireStation(address): " + i +
                    " fire stations deleted");
            fireStationRepository.deleteAll(fireStations);
        } else {
            LOGGER.debug("deleteFireStation(address): no fire station deleted");
        }
        return i;
    }

    /**
     * To delete a fire stations from its address.
     * @param station the station number of the fire station to be deleted
     */
    public Integer deleteFireStationByStation(Integer station) {
        List<FireStation> fireStations = fireStationRepository.findByStation(station);
        int i = fireStations.size();
        if (i > 0) {
            LOGGER.debug("deleteFireStation(station): " + i +
                    " fire stations deleted");
            fireStationRepository.deleteAll(fireStations);
        } else {
            LOGGER.debug("deleteFireStation(station): no fire station deleted");
        }
        return i;
    }

    /**
     * To get the list of fire stations assigned for an address
     * @param address the address for which we need a list of station number
     * @return the list of fire station number for this address
     */
    public List<Integer> getStations(String address) {
        List<FireStation> fireStations = fireStationRepository.findByAddress(address);
        List<Integer> stations = new ArrayList<>();
        int i = fireStations.size();
        if (i > 0) {
            LOGGER.info("getStations: " + i + " fire station found for the address " + address);
            stations = fireStations.stream().map(FireStation::getStation)
                    .collect(Collectors.toList());
        } else {
            LOGGER.error("getStations: No fire station found for the address " + address);
        }
        return stations;
    }

    public List<String> getAddresses(Integer station) {
        List<FireStation> fireStations = fireStationRepository.findByStation(station);
        List<String> addresses = new ArrayList<>();
        int i = fireStations.size();
        if (i > 0) {
            LOGGER.info("getAddresses: " + i + " addresses found for fire station " + station);
            addresses = fireStations.stream().map(FireStation::getAddress)
                    .distinct().collect(Collectors.toList());
        } else {
            LOGGER.error("getAddresses:  no address attached to this fire station " + station);
        }
        return addresses;
    }
}
