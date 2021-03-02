package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.ChildAlertDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest(properties = "command.line.runner.enabled=false")
@ActiveProfiles("test")
public class ChildAlertServiceTest {

    @Autowired
    private ChildAlertService childAlertService;

    @MockBean
    private AlertListsService alertListsService;

    private static PersonMedicalRecordDTO personMedicalRecord1;
    private static PersonMedicalRecordDTO personMedicalRecord2;
    private static PersonMedicalRecordDTO personMedicalRecord3;
    private static PersonMedicalRecordDTO personMedicalRecord4;
    private static PersonMedicalRecordDTO personMedicalRecord5;
    private static PersonMedicalRecordDTO personMedicalRecord6;

    @BeforeAll
    public static void init() {
        personMedicalRecord1 = new PersonMedicalRecordDTO();
        personMedicalRecord2 = new PersonMedicalRecordDTO();

        personMedicalRecord3 = new PersonMedicalRecordDTO();
        personMedicalRecord4 = new PersonMedicalRecordDTO();

        personMedicalRecord5 = new PersonMedicalRecordDTO();
        personMedicalRecord6 = new PersonMedicalRecordDTO();

        personMedicalRecord1.setFirstName("Baby");
        personMedicalRecord1.setLastName("Family12");
        personMedicalRecord1.setAddress("address12");
        personMedicalRecord1.setPhone("phone1" );
        personMedicalRecord1.setEmail("mail.test1@email.com");
        personMedicalRecord1.setAge(5);
        personMedicalRecord1.setMedications(new ArrayList<>());
        personMedicalRecord1.setAllergies(new ArrayList<>());

        personMedicalRecord2.setFirstName("Dad");
        personMedicalRecord2.setLastName("Family12");
        personMedicalRecord2.setAddress("address12");
        personMedicalRecord2.setPhone("phone1" );
        personMedicalRecord2.setEmail("mail.test1@email.com");
        personMedicalRecord2.setAge(43);
        personMedicalRecord2.setMedications(Arrays.asList("med1", "med2"));
        personMedicalRecord2.setAllergies(Arrays.asList("allergie2"));

        personMedicalRecord3.setFirstName("NearlyAdult");
        personMedicalRecord3.setLastName("Family12");
        personMedicalRecord3.setAddress("address12");
        personMedicalRecord3.setPhone("phone1" );
        personMedicalRecord3.setEmail("mail.test1@email.com");
        personMedicalRecord3.setAge(18);
        personMedicalRecord3.setMedications(Arrays.asList("med1"));
        personMedicalRecord3.setAllergies(new ArrayList<>());

        personMedicalRecord4.setFirstName("Mum");
        personMedicalRecord4.setLastName("Family12");
        personMedicalRecord4.setAddress("address12");
        personMedicalRecord4.setPhone("phone4" );
        personMedicalRecord4.setEmail("mail.test4@email.com");
        personMedicalRecord4.setAge(40);
        personMedicalRecord4.setMedications(new ArrayList<>());
        personMedicalRecord4.setAllergies(Arrays.asList("allergie4"));

        personMedicalRecord5.setFirstName("Mum");
        personMedicalRecord5.setLastName("OtherFamilyAddress12");
        personMedicalRecord5.setAddress("address12");
        personMedicalRecord5.setPhone("phone5" );
        personMedicalRecord5.setEmail("mail.test5@email.com");
        personMedicalRecord5.setAge(50);
        personMedicalRecord5.setMedications(new ArrayList<>());
        personMedicalRecord5.setAllergies(Arrays.asList("allergie5"));

        personMedicalRecord6.setFirstName("Babe");
        personMedicalRecord6.setLastName("OtherFamilyAddress12");
        personMedicalRecord6.setAddress("address12");
        personMedicalRecord6.setPhone("phone6" );
        personMedicalRecord6.setEmail("mail.test6@email.com");
        personMedicalRecord6.setAge(0);
        personMedicalRecord6.setMedications(new ArrayList<>());
        personMedicalRecord6.setAllergies(new ArrayList<>());

    }

    @BeforeEach
    public void initDtoTest() {

    }

    @Test
    public void getChildAlertListTest() {
        //GIVEN
        List<PersonMedicalRecordDTO> persons = Arrays.asList(personMedicalRecord1,
                personMedicalRecord2, personMedicalRecord3, personMedicalRecord4);
        when(alertListsService.getMedicalRecordByAddress("address12", false)).thenReturn(persons);

        //WHEN
        List<ChildAlertDTO> childAlertList = childAlertService.getChildAlertList("address12");

        //THEN
        assertThat(childAlertList.get(0).getHomehood()).isEqualTo("Family12");
        assertThat(childAlertList.get(0).getChildren().size()).isEqualTo(2);
        assertThat(childAlertList.get(0).getAdults().size()).isEqualTo(2);
        assertThat(childAlertList.get(0).getChildren().get(0).getAge()).isLessThan(19);
        assertThat(childAlertList.get(0).getChildren().get(1).getAge()).isLessThan(19);
        assertThat(childAlertList.get(0).getAdults().get(0).getAge()).isGreaterThan(18);
        assertThat(childAlertList.get(0).getAdults().get(1).getAge()).isGreaterThan(18);

    }

    @Test
    public void getChildAlertListWithNoChildAtAddress_shouldReturnEmptyList() {
        //GIVEN
        List<PersonMedicalRecordDTO> persons = Arrays.asList(personMedicalRecord2, personMedicalRecord4);
        when(alertListsService.getMedicalRecordByAddress("address12", false)).thenReturn(persons);

        //WHEN
        List<ChildAlertDTO> childAlertList = childAlertService.getChildAlertList("address12");

        //THEN
        assertThat(childAlertList.size()).isEqualTo(0);
    }

    @Test
    public void getChildAlertListWithNoAdultsTest() {
        //GIVEN
        List<PersonMedicalRecordDTO> persons = Arrays.asList(personMedicalRecord1,
                personMedicalRecord3);
        when(alertListsService.getMedicalRecordByAddress("address12", false)).thenReturn(persons);

        //WHEN
        List<ChildAlertDTO> childAlertList = childAlertService.getChildAlertList("address12");

        //THEN
        assertThat(childAlertList.get(0).getHomehood()).isEqualTo("Family12");
        assertThat(childAlertList.get(0).getChildren().size()).isEqualTo(2);
        assertThat(childAlertList.get(0).getAdults().size()).isEqualTo(0);
        assertThat(childAlertList.get(0).getChildren().get(0).getAge()).isLessThan(19);
        assertThat(childAlertList.get(0).getChildren().get(1).getAge()).isLessThan(19);

    }

    @Test
    public void getChildAlertListWith2FamiliesAtTheSameAddressTest() {
        //GIVEN
        List<PersonMedicalRecordDTO> persons = Arrays.asList(personMedicalRecord1,
                personMedicalRecord2, personMedicalRecord3, personMedicalRecord4, personMedicalRecord5,
                personMedicalRecord6);
        when(alertListsService.getMedicalRecordByAddress("address12", false)).thenReturn(persons);

        //WHEN
        List<ChildAlertDTO> childAlertList = childAlertService.getChildAlertList("address12");

        //THEN
        assertThat(childAlertList.get(0).getHomehood()).isEqualTo("Family12");
        assertThat(childAlertList.get(0).getChildren().size()).isEqualTo(2);
        assertThat(childAlertList.get(0).getAdults().size()).isEqualTo(2);
        assertThat(childAlertList.get(0).getChildren().get(0).getAge()).isLessThan(19);
        assertThat(childAlertList.get(0).getChildren().get(1).getAge()).isLessThan(19);

        assertThat(childAlertList.get(1).getHomehood()).isEqualTo("OtherFamilyAddress12");
        assertThat(childAlertList.get(1).getChildren().size()).isEqualTo(1);
        assertThat(childAlertList.get(1).getAdults().size()).isEqualTo(1);
        assertThat(childAlertList.get(1).getChildren().get(0).getAge()).isEqualTo(0);
        assertThat(childAlertList.get(1).getAdults().get(0).getAge()).isEqualTo(50);

    }

}
