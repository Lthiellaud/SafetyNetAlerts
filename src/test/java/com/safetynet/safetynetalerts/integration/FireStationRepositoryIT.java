package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class FireStationRepositoryIT {

    @Autowired
    FireStationRepository fireStationRepository;

    @Test
    public void findByStationTest() {
        //WHEN
        List<FireStation> fireStations = fireStationRepository.findByStation(1);

        //THEN
        assertThat(fireStations.size()).isEqualTo(3);
        assertThat(fireStations).containsExactlyInAnyOrder(
                new FireStation(9L,"address1-st1", 1), new FireStation(11L, "address2-st1", 1),
                new FireStation(13L, "address3-st1", 1));

    }

    @Test
    public void findByAddressTest() {
        //WHEN
        List<FireStation> fireStations = fireStationRepository.findByAddress("Address attached to 2 fire station");

        //THEN
        assertThat(fireStations.size()).isEqualTo(2);
        assertThat(fireStations).containsExactlyInAnyOrder(
                new FireStation(10L,"Address attached to 2 fire station", 3),
                new FireStation(12L, "Address attached to 2 fire station", 4));

    }

    @Test
    public void findAllByAddressAndStationTest() {
        //WHEN
        List<FireStation> fireStations = fireStationRepository.findAllByAddressAndStation("Address attached to 2 fire station", 3);

        //THEN
        assertThat(fireStations.size()).isEqualTo(1);
        assertThat(fireStations).containsExactlyInAnyOrder(
                new FireStation(10L,"Address attached to 2 fire station", 3));

    }
}
