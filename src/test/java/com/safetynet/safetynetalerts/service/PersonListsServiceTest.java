package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.*;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


import static java.util.Calendar.*;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "command.line.runner.enabled=false")
@ActiveProfiles("test")
public class PersonListsServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private FireStationRepository fireStationRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private static PersonListsService personListsService;

    private static Person person1;
    private static Person person2;
    private static Person person3;
    private static Person person4;
    private static List<PersonDto> firePersons;

    private static FireStation fireStation1;
    private static FireStation fireStation2;
    private static FireStation fireStation3;

    private static MedicalRecord medicalRecord1;
    private static MedicalRecord medicalRecord2;
    private static MedicalRecord medicalRecord3;
    private static MedicalRecord medicalRecord4;

    private Calendar calendar = Calendar.getInstance();

    public void InitDtoTest(){
        fireStation1 = new FireStation();
        fireStation2 = new FireStation();
        fireStation3 = new FireStation();
        fireStation1.setAddress("address12");
        fireStation1.setStation(12);
        fireStation1.setId((long) 1);

        fireStation2.setAddress("address3");
        fireStation2.setStation(3);
        fireStation2.setId((long) 2);

        fireStation3.setAddress("address4");
        fireStation3.setStation(4);
        fireStation3.setId((long) 3);

        person1 = new Person();
        person2 = new Person();
        person3 = new Person();
        person4 = new Person();

        person1.setFirstName("Baby");
        person1.setLastName("Family12");
        person1.setAddress("address12");
        person1.setCity("Culver");
        person1.setZip(97451);
        person1.setPhone("phone1" );
        person1.setEmail("mail.test1@email.com");

        person2.setFirstName("Dad");
        person2.setLastName("Family12");
        person2.setAddress("address12");
        person2.setCity("Culver");
        person2.setZip(97458);
        person2.setPhone("phone1" );
        person2.setEmail("mail.test1@email.com");

        person3.setFirstName("John");
        person3.setLastName("Family3");
        person3.setAddress("address3");
        person3.setCity("Culver");
        person3.setZip(97458);
        person3.setPhone("phone3" );
        person3.setEmail("mail.test3@email.com");

        person4.setFirstName("Suzan");
        person4.setLastName("Family4");
        person4.setAddress("address4");
        person4.setCity("Culver");
        person4.setZip(97458);
        person4.setPhone("phone4" );
        person4.setEmail("mail.test4@email.com");

        calendar.set(2021, JANUARY, 17);
        medicalRecord1.setFirstName("Baby");
        medicalRecord1.setLastName("Family12");
        medicalRecord1.setBirthdate(calendar.getTime());
        medicalRecord1.setMedications(new ArrayList<>());
        medicalRecord1.setAllergies(new ArrayList<>());

        calendar.set(1985, MARCH, 17);
        medicalRecord2.setFirstName("Dad");
        medicalRecord2.setLastName("Family12");
        medicalRecord2.setBirthdate(calendar.getTime());
        medicalRecord2.setMedications(Arrays.asList("med1", "med2"));
        medicalRecord2.setAllergies(Arrays.asList("allergie2"));

        calendar.set(2003, FEBRUARY, 11);
        medicalRecord3.setFirstName("John");
        medicalRecord3.setLastName("Family3");
        medicalRecord3.setBirthdate(calendar.getTime());
        medicalRecord3.setMedications(Arrays.asList("med3"));
        medicalRecord3.setAllergies(new ArrayList<>());

        calendar.set(2003, FEBRUARY, 12);
        medicalRecord4.setFirstName("Suzan");
        medicalRecord4.setLastName("Family4");
        medicalRecord4.setBirthdate(calendar.getTime());
        medicalRecord4.setMedications(new ArrayList<>());
        medicalRecord4.setAllergies(Arrays.asList("allergie4", "allergie5"));


    }
    @Test
    public void getEmailListTest(){
        //GIVEN
        when(personRepository.findAllDistinctEmailByCity("City")).thenReturn(anyList());

        //WHEN
        personListsService.getEmailList("City");

        //THEN
        verify(personRepository, times(1)).findAllDistinctEmailByCity("City");

    }
    @Test
    public void getFirePersonListTest(){
        /*//GIVEN
        when(personRepository.findAllByAddress("address1")).thenReturn(anyList());
        when(fireStationRepository.findAllByAddress("address1")).thenReturn(anyList());

        //WHEN
        personListsService.getEmailList("City");

        //THEN
        verify(personRepository, times(1)).findAllDistinctEmailByCity("City");
*/
    }
}
