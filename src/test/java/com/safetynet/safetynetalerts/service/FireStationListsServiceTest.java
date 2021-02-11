package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.IPersonPhone;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "command.line.runner.enabled=false")
@ActiveProfiles("test")
public class FireStationListsServiceTest {

    @MockBean
    private FireStationRepository fireStationRepository;
    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private FireStationListsService fireStationListsService;

    private static List<FireStation> fireStations;
    private static FireStation fireStation1;
    private static FireStation fireStation2;

    private static List<IPersonPhone> personPhones;
    private static IPersonPhone phone1;
    private static IPersonPhone phone4;


    @BeforeEach
    public void setInit() {
        fireStation1 = new FireStation();
        fireStation2 = new FireStation();
        fireStation1.setAddress("address1");
        fireStation1.setStation(1);
        fireStation1.setId((long) 1);
        fireStation2.setAddress("address2");
        fireStation2.setStation(1);
        fireStation2.setId((long) 2);

    }

    @Test
    public void getAddressesTest(){
        //GIVEN
        fireStations = Arrays.asList(fireStation1, fireStation1, fireStation2);
        when(fireStationRepository.findDistinctByStation(1)).thenReturn(fireStations);

        //WHEN
        Iterable<String> addresses = fireStationListsService.getAddresses(1);

        //THEN
        verify(fireStationRepository, times(1)).findDistinctByStation(1);
        assertThat(addresses).containsExactlyElementsOf(Arrays.asList("address1", "address2"));
    }

    @Test
    public void getPhonesTest(){
        //GIVEN
        fireStations = Arrays.asList(fireStation1, fireStation2);
        when(fireStationRepository.findDistinctByStation(1)).thenReturn(fireStations);
        when(personRepository.findAllDistinctPhoneByAddressIsIn(any())).thenReturn(anyList());

        //WHEN
        List<IPersonPhone> phones = fireStationListsService.getPhones(1);

        //THEN
        verify(personRepository, times(1)).findAllDistinctPhoneByAddressIsIn(any());
        verify(fireStationRepository, times(1)).findDistinctByStation(1);
        /*assertThat(phones.get(0).getPhone()).isEqualTo("phone1");
        assertThat(phones.get(1).getPhone()).isEqualTo("phone4");
        assertThat(phones.size()).isEqualTo(2);*/
    }
}
