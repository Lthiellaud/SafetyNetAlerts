package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {

    /**
     * To retrieve a list of fire station from a station number.
     * @param station the station number
     * @return the list of fire station records
     */
    List<FireStation> findByStation(Integer station);

    /**
     * To retrieve the list of fire stations attached to an address.
     * @param address the given address
     * @return the list of fire station records
     */
    List<FireStation> findByAddress(String address);

    /**
     * To retrieve the list of fire stations with attached to an addressa specified station number.
     * @param address the given address
     * @param station the station number
     * @return the list of fire station records
     */
    List<FireStation> findAllByAddressAndStation(String address, Integer station);


}
