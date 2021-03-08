package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {

    List<FireStation> findByStation(Integer station);
    List<FireStation> findByAddress(String address);
    List<FireStation> findAllByAddressAndStation(String address, Integer station);


}
