package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public FireStation saveFireStation(FireStation fireStation) {
        return fireStationRepository.save(fireStation);
    }

    public Optional<FireStation> getFireStation(String address) {
        return fireStationRepository.findById(address);
    }

    public void deleteFireStation(String address) {
        fireStationRepository.deleteById(address);
    }

    public void deleteFireStation(Integer station) {
        fireStationRepository.deleteAll(fireStationRepository.findByStation(station));
    }
}
