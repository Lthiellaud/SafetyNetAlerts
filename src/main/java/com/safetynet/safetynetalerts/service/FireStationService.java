package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationService {
    /**
     * To add a new fire station.
     * @param fireStation the fire station to be added
     * @return the added fire station station or an empty optional if nothing done
     */
    Optional<FireStation> createFireStation(FireStation fireStation);

    /**
     * To update the fire station number for an address.
     * @param address the address to be updated
     * @param station th new fire station number
     * @return the updated fire station or an empty list if nothing done
     */
    List<FireStation> updateFireStation(String address, Integer station);

    /**
     * To add a list of new fire stations.
     * @param fireStations the fire station to be added
     * @return the added fire station station or an empty list if nothing done
     */
    Iterable<FireStation> saveFireStationList(List<FireStation> fireStations);

    /**
     * To delete a fire stations from its address.
     * @param address the address of the fire station to be deleted
     * @return the number of deleted record
     */
    Integer deleteFireStationByAddress(String address);

    /**
     * To delete a fire stations from its address.
     * @param station the station number of the fire station to be deleted
     * @return the number of deleted record
     */
    Integer deleteFireStationByStation(Integer station);

    /**
     * To get the list of fire stations assigned for an address.
     * @param address the address for which we need a list of station number
     * @return the list of fire station number for this address
     */
    List<Integer> getStations(String address);

    /**
     * To get the list of addresses attached to a fire station identified by its station number.
     * @param station the given station number
     * @return the list of addresses
     */
    List<String> getAddresses(Integer station);
}
