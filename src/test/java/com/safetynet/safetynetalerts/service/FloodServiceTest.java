package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.FloodDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "command.line.runner.enabled=false")
public class FloodServiceTest {

    @MockBean
    AlertListsService alertListsService;

    @MockBean
    FireStationService fireStationService;

    @Autowired
    private FloodService floodService;

    private static PersonPhoneMedicalRecordDTO personPhoneMedicalRecord1;
    private static PersonPhoneMedicalRecordDTO personPhoneMedicalRecord2;
    private static PersonPhoneMedicalRecordDTO personPhoneMedicalRecord3;
    private static PersonPhoneMedicalRecordDTO personPhoneMedicalRecord4;
    private static PersonPhoneMedicalRecordDTO personPhoneMedicalRecord5;

    @BeforeAll
    public static void init() {
        personPhoneMedicalRecord1 = new PersonPhoneMedicalRecordDTO();
        personPhoneMedicalRecord2 = new PersonPhoneMedicalRecordDTO();
        personPhoneMedicalRecord3 = new PersonPhoneMedicalRecordDTO();
        personPhoneMedicalRecord4 = new PersonPhoneMedicalRecordDTO();
        personPhoneMedicalRecord5 = new PersonPhoneMedicalRecordDTO();

        personPhoneMedicalRecord1.setFirstName("Baby");
        personPhoneMedicalRecord1.setLastName("Family11");
        personPhoneMedicalRecord1.setPhone("phone1" );
        personPhoneMedicalRecord1.setAge(5);
        personPhoneMedicalRecord1.setMedications(new ArrayList<>());
        personPhoneMedicalRecord1.setAllergies(new ArrayList<>());

        personPhoneMedicalRecord2.setFirstName("Dad");
        personPhoneMedicalRecord2.setLastName("Family11");
        personPhoneMedicalRecord2.setPhone("phone1" );
        personPhoneMedicalRecord2.setAge(43);
        personPhoneMedicalRecord2.setMedications(Arrays.asList("med1", "med2"));
        personPhoneMedicalRecord2.setAllergies(Arrays.asList("allergie2"));

        personPhoneMedicalRecord3.setFirstName("NearlyAdult");
        personPhoneMedicalRecord3.setLastName("Family12");
        personPhoneMedicalRecord3.setPhone("phone3" );
        personPhoneMedicalRecord3.setAge(18);
        personPhoneMedicalRecord3.setMedications(Arrays.asList("med1"));
        personPhoneMedicalRecord3.setAllergies(new ArrayList<>());

        personPhoneMedicalRecord4.setFirstName("Mum");
        personPhoneMedicalRecord4.setLastName("Family20");
        personPhoneMedicalRecord4.setPhone("phone4" );
        personPhoneMedicalRecord4.setAge(40);
        personPhoneMedicalRecord4.setMedications(new ArrayList<>());
        personPhoneMedicalRecord4.setAllergies(Arrays.asList("allergie4"));

        personPhoneMedicalRecord5.setFirstName("Mum");
        personPhoneMedicalRecord5.setLastName("Family21");
        personPhoneMedicalRecord5.setPhone("phone20" );
        personPhoneMedicalRecord5.setAge(50);
        personPhoneMedicalRecord5.setMedications(new ArrayList<>());
        personPhoneMedicalRecord5.setAllergies(Arrays.asList("allergie20"));

    }

    @Test
    public void getFloodListTest() {
        //GIVEN
        List<String> addresses1 = Arrays.asList("address11", "address12");
        List<String> addresses2 = Arrays.asList("address20");
        List<PersonPhoneMedicalRecordDTO> persons1 = Arrays.asList(personPhoneMedicalRecord1, personPhoneMedicalRecord2);
        List<PersonPhoneMedicalRecordDTO> persons2 = Arrays.asList(personPhoneMedicalRecord3);
        List<PersonPhoneMedicalRecordDTO> persons3 = Arrays.asList(personPhoneMedicalRecord4);
        when(fireStationService.getAddresses(1)).thenReturn(addresses1);
        when(fireStationService.getAddresses(2)).thenReturn(addresses2);
        when(alertListsService.getPersonPhoneMedicalRecordDTO("address11")).thenReturn(persons1);
        when(alertListsService.getPersonPhoneMedicalRecordDTO("address12")).thenReturn(persons2);
        when(alertListsService.getPersonPhoneMedicalRecordDTO("address20")).thenReturn(persons3);

        //WHEN
        List<FloodDTO> persons = floodService.getFloodList(Arrays.asList(1,2));

        //THEN
        verify(fireStationService, times(1)).getAddresses(1);
        verify(fireStationService, times(1)).getAddresses(2);
        verify(alertListsService, times(1)).getPersonPhoneMedicalRecordDTO("address11");
        verify(alertListsService, times(1)).getPersonPhoneMedicalRecordDTO("address12");
        verify(alertListsService, times(1)).getPersonPhoneMedicalRecordDTO("address20");
        assertThat(persons.size()).isEqualTo(2);
        assertThat(persons.get(0).getAddresses().size()).isEqualTo(2);
        assertThat(persons.get(1).getAddresses().size()).isEqualTo(1);
        assertThat(persons.get(0).getAddresses().get(0).getPersons().get(0).getAge()).isEqualTo(5);
        assertThat(persons.get(0).getAddresses().get(0).getPersons().get(1).getAllergies()).containsExactly("allergie2");
        assertThat(persons.get(0).getAddresses().get(1).getPersons().get(0).getPhone()).isEqualTo("phone3");
        assertThat(persons.get(1).getAddresses().get(0).getPersons().get(0).getLastName()).isEqualTo("Family20");
        assertThat(persons.get(1).getAddresses().get(0).getAddress()).isEqualTo("address20");

    }

    @Test
    public void getFloodList_nobodyFoundForAddressesTest() {
        //GIVEN
        List<String> addresses1 = Arrays.asList("address11", "address12");
        List<String> addresses2 = Arrays.asList("address20");
        List<PersonPhoneMedicalRecordDTO> persons1 = new ArrayList<>();
        List<PersonPhoneMedicalRecordDTO> persons2 = new ArrayList<>();
        List<PersonPhoneMedicalRecordDTO> persons3 = new ArrayList<>();
        when(fireStationService.getAddresses(1)).thenReturn(addresses1);
        when(fireStationService.getAddresses(2)).thenReturn(addresses2);
        when(alertListsService.getPersonPhoneMedicalRecordDTO("address11")).thenReturn(persons1);
        when(alertListsService.getPersonPhoneMedicalRecordDTO("address12")).thenReturn(persons2);
        when(alertListsService.getPersonPhoneMedicalRecordDTO("address20")).thenReturn(persons3);

        //WHEN
        List<FloodDTO> persons = floodService.getFloodList(Arrays.asList(1,2));

        //THEN
        verify(fireStationService, times(1)).getAddresses(1);
        verify(fireStationService, times(1)).getAddresses(2);
        verify(alertListsService, times(1)).getPersonPhoneMedicalRecordDTO("address11");
        verify(alertListsService, times(1)).getPersonPhoneMedicalRecordDTO("address12");
        verify(alertListsService, times(1)).getPersonPhoneMedicalRecordDTO("address20");
        assertThat(persons).isEmpty();

    }

    @Test
    public void getFloodList_noAddressFoundTest() {
        //GIVEN
        List<String> addresses1 = new ArrayList<>();
        when(fireStationService.getAddresses(1)).thenReturn(addresses1);

        //WHEN
        List<FloodDTO> persons = floodService.getFloodList(Arrays.asList(1));

        //THEN
        verify(fireStationService, times(1)).getAddresses(1);
        verify(alertListsService, times(0)).getPersonPhoneMedicalRecordDTO(anyString());
        assertThat(persons.size()).isEqualTo(0);

    }
}
