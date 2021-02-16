package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    /**
     * To add a new fire station.
     * @param fireStation the fire station to be added
     * @return the added fire station station
     */
    public FireStation saveFireStation(FireStation fireStation) {
        return fireStationRepository.save(fireStation);
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
     * To get a fire stations from its address.
     * @param address the address of the fire station to be gotten
     * @return the founded fire station
     */
    public Iterable<FireStation> getFireStation(String address) {
        return fireStationRepository.findByAddress(address);
    }

    /**
     * To delete a fire stations from its address.
     * @param address the address of the fire station to be deleted
     */
    public void deleteFireStation(String address) {
        fireStationRepository.deleteAll(fireStationRepository.findByAddress(address));
    }

    /**
     * To delete a fire stations from its address.
     * @param station the station number of the fire station to be deleted
     */
    public void deleteFireStation(Integer station) {
        fireStationRepository.deleteAll(fireStationRepository.findByStation(station));
    }

    /**
     * To get the list of fire stations assigned for an address
     * @param address the address for which we need a list of station number
     * @return the list of fire station number for this address
     */
    public List<Integer> getStations(String address) {
        List<FireStation> fireStations = fireStationRepository.findAllByAddress(address);
        List<Integer> stations = new ArrayList<>();
        if (fireStations.size() > 0) {
            stations = fireStations.stream().map(FireStation::getStation)
                    .collect(Collectors.toList());
        }
        return stations;
    }

    public List<String> getAddresses(Integer station) {
        List<FireStation> fireStations = fireStationRepository.findByStation(station);
        List<String> addresses = new ArrayList<>();
        if (fireStations != null) {
            addresses = fireStations.stream().map(FireStation::getAddress)
                    .distinct().collect(Collectors.toList());
        }
        return addresses;
    }



}
