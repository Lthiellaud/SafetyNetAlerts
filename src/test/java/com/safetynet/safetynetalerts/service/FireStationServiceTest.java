package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "command.line.runner.enabled=false")
public class FireStationServiceTest {

    @MockBean
    FireStationRepository fireStationRepository;

    @Autowired
    FireStationService fireStationService;

    private static FireStation fireStation1;
    private static FireStation fireStation2;
    private static FireStation fireStation3;

    @BeforeEach
    public void initTest() {
        fireStation1 = new FireStation(1L, "add 1", 1);
        fireStation2 = new FireStation(2L, "add 2", 1);
        fireStation3 = new FireStation(3L, "add 1", 2);

    }
    @Test
    public void createFireStationTest() {
        //GIVEN
        FireStation fireStation = new FireStation(null, "add 1", 1);
        when(fireStationRepository
                .findAllByAddressAndStation("add 1", 1)).thenReturn(new ArrayList<>());
        when(fireStationRepository.save(fireStation))
                .thenReturn(fireStation1);

        //THEN
        assertThat(fireStationService.createFireStation(fireStation).isPresent()).isTrue();
        verify(fireStationRepository, times(1))
                .findAllByAddressAndStation("add 1", 1);
        verify(fireStationRepository, times(1))
                .save(new FireStation(null, "add 1", 1));
    }

    @Test
    public void tryToCreateExistingFireStationTest() {
        //GIVEN
        when(fireStationRepository
                .findAllByAddressAndStation("add 1", 1)).thenReturn(Arrays.asList(fireStation1));

        //THEN
        assertThat(fireStationService.
                createFireStation(new FireStation(null, "add 1", 1)).isPresent()).isFalse();
        verify(fireStationRepository, times(1))
                .findAllByAddressAndStation("add 1", 1);
        verify(fireStationRepository, times(0))
                .save(new FireStation(null, "add 1", 1));
    }

    @Test
    public void updateFireStation() {
        List<FireStation> fireStations = Arrays.asList(fireStation1, fireStation3);
        when(fireStationRepository.findByAddress("add 1")).thenReturn(fireStations);
        when(fireStationRepository.save(fireStation1)).thenReturn(any(FireStation.class));
        when(fireStationRepository.save(fireStation3)).thenReturn(any(FireStation.class));

        Iterable<FireStation> result = fireStationService.updateFireStation("add 1", 10);

        verify(fireStationRepository, times(1)).findByAddress("add 1");
        verify(fireStationRepository, times(2)).save(any(FireStation.class));
        assertThat(result).containsExactlyInAnyOrder(fireStation1, fireStation3);
        assertThat(fireStation1.getStation()).isEqualTo(10);
        assertThat(fireStation3.getStation()).isEqualTo(10);

    }
    @Test
    public void tryToUpdateNonExistingFireStation() {
        //GIVEN
        List<FireStation> fireStations = new ArrayList<>();
        when(fireStationRepository.findByAddress("add 1")).thenReturn(fireStations);

        //WHEN
        Iterable<FireStation> result = fireStationService.updateFireStation("add 1", 10);

        //THEN
        verify(fireStationRepository, times(1)).findByAddress("add 1");
        verify(fireStationRepository, times(0)).save(any(FireStation.class));
        assertThat(result).isEmpty();
    }

    @Test
    public void tryToUpdateFireStationWithNullFireStationNumber() {
        //WHEN
        Iterable<FireStation> result = fireStationService.updateFireStation("add 1", null);

        //THEN
        verify(fireStationRepository, times(0)).findByAddress(anyString());
        verify(fireStationRepository, times(0)).save(any(FireStation.class));
        assertThat(result).isEmpty();
    }

    @Test
    public void saveFireStationListTest(){
        //GIVEN
        List<FireStation> fireStations = Arrays.asList(fireStation1, fireStation2);
        when(fireStationRepository.saveAll(fireStations)).thenReturn(fireStations);

        //THEN
        assertThat(fireStationService.saveFireStationList(fireStations)).containsExactly(fireStation1, fireStation2);
        verify(fireStationRepository,times(1)).saveAll(fireStations);
    }

    @Test
    public void getStationsByAddressTest() {
        List<FireStation> fireStations = Arrays.asList(fireStation1, fireStation3);
        when(fireStationRepository.findByAddress("add 1")).thenReturn(fireStations);

        List<Integer> stations = fireStationService.getStations("add 1");

        verify(fireStationRepository, times(1)).findByAddress("add 1");
        assertThat(stations).containsExactlyInAnyOrder(1, 2);
    }

    @Test
    public void getStationForNonExistingAddressTest() {
        List<FireStation> fireStations = new ArrayList<>();
        when(fireStationRepository.findByAddress("add new")).thenReturn(fireStations);

        List<Integer> stations = fireStationService.getStations("add new");

        verify(fireStationRepository, times(1)).findByAddress("add new");
        assertThat(stations.size()).isEqualTo(0);
    }

    @Test
    public void getAddressesByStationTest() {
        List<FireStation> fireStations = Arrays.asList(fireStation1, fireStation2);
        when(fireStationRepository.findByStation(1)).thenReturn(fireStations);

        List<String> addresses = fireStationService.getAddresses(1);

        verify(fireStationRepository, times(1)).findByStation(1);
        assertThat(addresses).containsExactlyInAnyOrder("add 1", "add 2");
    }

    @Test
    public void getAddressesForNonExistingStationTest() {
        List<FireStation> fireStations = new ArrayList<>();
        when(fireStationRepository.findByStation(8)).thenReturn(fireStations);

        List<String> addresses = fireStationService.getAddresses(8);

        verify(fireStationRepository, times(1)).findByStation(8);
        assertThat(addresses.size()).isEqualTo(0);
    }

    @Test
    public void deleteFireStationByAddressTest() {
        List<FireStation> fireStations = Arrays.asList(fireStation1, fireStation3);
        when(fireStationRepository.findByAddress("add 1")).thenReturn(fireStations);

        fireStationService.deleteFireStation("add 1");

        verify(fireStationRepository, times(1)).findByAddress("add 1");
        verify(fireStationRepository, times(1)).deleteAll(fireStations);
    }

    @Test
    public void deleteNonExistingFireStationByAddressTest() {
        List<FireStation> fireStations = new ArrayList<>();
        when(fireStationRepository.findByAddress("add new")).thenReturn(fireStations);

        fireStationService.deleteFireStation("add new");

        verify(fireStationRepository, times(1)).findByAddress("add new");
        verify(fireStationRepository, times(0)).deleteAll(fireStations);
    }

    @Test
    public void deleteFireStationByStationTest() {
        List<FireStation> fireStations = Arrays.asList(fireStation1, fireStation2);
        when(fireStationRepository.findByStation(1)).thenReturn(fireStations);

        fireStationService.deleteFireStation(1);

        verify(fireStationRepository, times(1)).findByStation(1);
        verify(fireStationRepository, times(1)).deleteAll(fireStations);
    }

    @Test
    public void deleteNonExistingFireStationByStationTest() {
        List<FireStation> fireStations = new ArrayList<>();
        when(fireStationRepository.findByStation(8)).thenReturn(fireStations);

        fireStationService.deleteFireStation(8);

        verify(fireStationRepository, times(1)).findByStation(8);
        verify(fireStationRepository, times(0)).deleteAll(fireStations);
    }

}
