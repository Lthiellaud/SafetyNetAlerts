package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.service.implementation.AlertListsServiceImpl;
import com.safetynet.safetynetalerts.service.implementation.MedicalRecordServiceImpl;
import com.safetynet.safetynetalerts.service.implementation.PersonServiceImpl;
import com.safetynet.safetynetalerts.util.DateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static java.util.Calendar.JANUARY;
import static java.util.Calendar.MARCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "command.line.runner.enabled=false")
public class AlertListsServiceTest {

    @MockBean
    private PersonServiceImpl personService;

    @MockBean
    private MedicalRecordServiceImpl medicalRecordService;

    @Autowired
    private AlertListsServiceImpl alertListsService;

    private static PersonId personId1;
    private static PersonId personId2;

    private static MedicalRecord medicalRecord1;
    private static MedicalRecord medicalRecord2;

    private static PersonMedicalRecordDTO personMedicalRecord1;
    private static PersonMedicalRecordDTO personMedicalRecord2;

    private static Date birthdate1;
    private static Date birthdate2;

    private static DateUtil dateUtil;


    private static Calendar calendar = Calendar.getInstance();

    @BeforeAll
    public static void init() {
        personId1 = new PersonId("Baby", "Family12");
        personId2 = new PersonId("Dad", "Family12");

        medicalRecord1 = new MedicalRecord();
        medicalRecord2 = new MedicalRecord();

        calendar.set(2021, JANUARY, 17);
        birthdate1 = calendar.getTime();
        medicalRecord1.setFirstName("Baby");
        medicalRecord1.setLastName("Family12");
        medicalRecord1.setBirthdate(birthdate1);
        medicalRecord1.setMedications(new ArrayList<>());
        medicalRecord1.setAllergies(new ArrayList<>());

        calendar.set(1985, MARCH, 17);
        birthdate2 = calendar.getTime();
        medicalRecord2.setFirstName("Dad");
        medicalRecord2.setLastName("Family12");
        medicalRecord2.setBirthdate(birthdate2);
        medicalRecord2.setMedications(Arrays.asList("med1", "med2"));
        medicalRecord2.setAllergies(Arrays.asList("allergie2"));
    }

    @BeforeEach
    public void initDtoTest() {
        dateUtil = new DateUtil();

        personMedicalRecord1 = new PersonMedicalRecordDTO();
        personMedicalRecord2 = new PersonMedicalRecordDTO();

        personMedicalRecord1.setFirstName("Baby");
        personMedicalRecord1.setLastName("Family12");
        personMedicalRecord1.setAddress("address12");
        personMedicalRecord1.setPhone("phone1" );
        personMedicalRecord1.setEmail("mail.test1@email.com");

        personMedicalRecord2.setFirstName("Dad");
        personMedicalRecord2.setLastName("Family12");
        personMedicalRecord2.setAddress("address12");
        personMedicalRecord2.setPhone("phone1" );
        personMedicalRecord2.setEmail("mail.test1@email.com");


    }

    @Test
    public void getMedicalRecordTest(){
        //GIVEN
        List<PersonMedicalRecordDTO> personMedicalRecords = Arrays.asList(personMedicalRecord1, personMedicalRecord2);
        when(medicalRecordService.getMedicalRecord(personId1)).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordService.getMedicalRecord(personId2)).thenReturn(Optional.of(medicalRecord2));

        //WHEN
        alertListsService.getMedicalRecord(personMedicalRecords, false);

        //THEN
        assertThat(personMedicalRecords.size()).isEqualTo(2);
        assertThat(personMedicalRecords.get(0).getAge()).isEqualTo(dateUtil.age(birthdate1));
        assertThat(personMedicalRecords.get(0).getAllergies()).isNullOrEmpty();
        assertThat(personMedicalRecords.get(0).getMedications()).isNullOrEmpty();
        assertThat(personMedicalRecords.get(1).getAge()).isEqualTo(dateUtil.age(birthdate2));
        assertThat(personMedicalRecords.get(1).getAllergies()).containsExactly("allergie2");
        assertThat(personMedicalRecords.get(1).getMedications()).containsExactly("med1", "med2");

    }

    @Test
    public void TryToGetNonExistingMedicalRecordTest(){
        //GIVEN
        List<PersonMedicalRecordDTO> personMedicalRecords = Arrays.asList(personMedicalRecord1, personMedicalRecord2);
        when(medicalRecordService.getMedicalRecord(personId1)).thenReturn(Optional.empty());
        when(medicalRecordService.getMedicalRecord(personId2)).thenReturn(Optional.empty());

        //WHEN
        alertListsService.getMedicalRecord(personMedicalRecords, false);

        //THEN
        assertThat(personMedicalRecords.size()).isEqualTo(2);
        assertThat(personMedicalRecords.get(0).getAge()).isEqualTo(0);
        assertThat(personMedicalRecords.get(0).getAllergies()).isNullOrEmpty();
        assertThat(personMedicalRecords.get(0).getMedications()).isNullOrEmpty();
        assertThat(personMedicalRecords.get(1).getAge()).isEqualTo(0);
        assertThat(personMedicalRecords.get(1).getAllergies()).isNullOrEmpty();
        assertThat(personMedicalRecords.get(1).getMedications()).isNullOrEmpty();

    }

    @Test
    public void getMedicalRecordByAddressTest() {
        //GIVEN
        List<PersonMedicalRecordDTO> personMedicalRecords = Arrays.asList(personMedicalRecord1, personMedicalRecord2);
        when(personService.getAllByAddress("address12")).thenReturn(personMedicalRecords);
        when(medicalRecordService.getMedicalRecord(personId1)).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordService.getMedicalRecord(personId2)).thenReturn(Optional.of(medicalRecord2));

        //WHEN
        List<PersonMedicalRecordDTO> persons = alertListsService.getMedicalRecordByAddress("address12", false);

        //THEN
        assertThat(persons.size()).isEqualTo(2);
        assertThat(persons.get(0).getAge()).isEqualTo(dateUtil.age(birthdate1));
        assertThat(persons.get(0).getAllergies()).isNullOrEmpty();
        assertThat(persons.get(0).getMedications()).isNullOrEmpty();
        assertThat(persons.get(1).getAge()).isEqualTo(dateUtil.age(birthdate2));
        assertThat(persons.get(1).getAllergies()).containsExactly("allergie2");
        assertThat(persons.get(1).getMedications()).containsExactly("med1", "med2");
    }

    @Test
    public void getMedicalRecordByAddress_ageOnlyTest() {
        //GIVEN
        List<PersonMedicalRecordDTO> personMedicalRecords = Arrays.asList(personMedicalRecord1, personMedicalRecord2);
        when(personService.getAllByAddress("address12")).thenReturn(personMedicalRecords);
        when(medicalRecordService.getMedicalRecord(personId1)).thenReturn(Optional.of(medicalRecord1));
        when(medicalRecordService.getMedicalRecord(personId2)).thenReturn(Optional.of(medicalRecord2));

        //WHEN
        List<PersonMedicalRecordDTO> persons = alertListsService.getMedicalRecordByAddress("address12", true);

        //THEN
        assertThat(persons.size()).isEqualTo(2);
        assertThat(persons.get(0).getAge()).isEqualTo(dateUtil.age(birthdate1));
        assertThat(persons.get(0).getAllergies()).isNullOrEmpty();
        assertThat(persons.get(0).getMedications()).isNullOrEmpty();
        assertThat(persons.get(1).getAge()).isEqualTo(dateUtil.age(birthdate2));
        assertThat(persons.get(1).getAllergies()).isNullOrEmpty();
        assertThat(persons.get(1).getMedications()).isNullOrEmpty();
    }

}
