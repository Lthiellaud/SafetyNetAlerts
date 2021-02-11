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
import static org.mockito.Mockito.when;

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
    /*private static FireStation fireStation3;

    private static Person person1;
    private static Person person2;
    private static Person person3;
    private static Person person4;*/
    private static List<IPersonPhone> personPhones;
    private static IPersonPhone phone1;
    private static IPersonPhone phone4;

/*
    private static MedicalRecord medicalRecord1;
    private static MedicalRecord medicalRecord2;
    private static MedicalRecord medicalRecord3;
*/


    @BeforeEach
    public void setInit() {
        fireStation1 = new FireStation();
        fireStation2 = new FireStation();
        //fireStation3 = new FireStation();
        fireStation1.setAddress("address1");
        fireStation1.setStation(1);
        fireStation1.setId((long) 1);
        fireStation2.setAddress("address2");
        fireStation2.setStation(1);
        fireStation2.setId((long) 2);
    /*    fireStation3.setAddress("address3");
        fireStation3.setStation(2);
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
        person4.setEmail("mail.test4@email.com");*/
    }

    @Test
    public void getAddressesTest(){
        //GIVEN
        fireStations = Arrays.asList(fireStation1, fireStation2);
        when(fireStationRepository.findDistinctByStation(1)).thenReturn(fireStations);

        //WHEN
        Iterable<String> addresses = fireStationListsService.getAddresses(1);

        //THEN
        assertThat(addresses).containsExactlyElementsOf(Arrays.asList("address1", "address2"));
    }

    @Test
    public void getPhonesTest(){
        //GIVEN
        fireStations = Arrays.asList(fireStation1, fireStation2);
        phone1 = () -> "phone1";
        phone4 = () -> "phone4";
        personPhones = Arrays.asList(phone1, phone4);
        when(fireStationRepository.findDistinctByStation(1)).thenReturn(fireStations);
        when(personRepository.findAllDistinctPhoneByAddressIsIn(any())).thenReturn(personPhones);

        //WHEN
        List<IPersonPhone> phones = (List<IPersonPhone>) fireStationListsService.getPhones(1);

        //THEN
        assertThat(phones.get(0).getPhone()).isEqualTo("phone1");
        assertThat(phones.get(1).getPhone()).isEqualTo("phone4");
        assertThat(phones.size()).isEqualTo(2);
    }
}
