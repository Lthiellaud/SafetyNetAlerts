package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.ChildAlertDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonAndCountByFireStationDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
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
public class PersonAndCountByFireStationServiceTest {

    @MockBean
    FireStationService fireStationService;

    @MockBean
    AlertListsService alertListsService;

    @Autowired
    PersonAndCountByFireStationService personAndCountByFireStationService;

    private static Person person1;
    private static Person person2;

    private static PersonId personId1;
    private static PersonId personId2;

    private static PersonMedicalRecordDTO personMedicalRecord1;
    private static PersonMedicalRecordDTO personMedicalRecord2;
    private static PersonMedicalRecordDTO personMedicalRecord3;
    private static PersonMedicalRecordDTO personMedicalRecord4;
    private static PersonMedicalRecordDTO personMedicalRecord5;
    private static PersonMedicalRecordDTO personMedicalRecord6;

    private static ChildAlertDTO childAlertDTO;

    @BeforeAll
    public static void init() {
        personId1 = new PersonId("Baby", "Family12");
        personId2 = new PersonId("Dad", "Family12");

        person1 = new Person();
        person2 = new Person();

        personMedicalRecord1 = new PersonMedicalRecordDTO();
        personMedicalRecord2 = new PersonMedicalRecordDTO();

        personMedicalRecord3 = new PersonMedicalRecordDTO();
        personMedicalRecord4 = new PersonMedicalRecordDTO();

        personMedicalRecord5 = new PersonMedicalRecordDTO();
        personMedicalRecord6 = new PersonMedicalRecordDTO();

        personMedicalRecord1.setFirstName("Baby");
        personMedicalRecord1.setLastName("Family11");
        personMedicalRecord1.setAddress("address11");
        personMedicalRecord1.setPhone("phone1" );
        personMedicalRecord1.setEmail("mail.test1@email.com");
        personMedicalRecord1.setAge(5);

        personMedicalRecord2.setFirstName("Dad");
        personMedicalRecord2.setLastName("Family11");
        personMedicalRecord2.setAddress("address11");
        personMedicalRecord2.setPhone("phone1" );
        personMedicalRecord2.setEmail("mail.test1@email.com");
        personMedicalRecord2.setAge(43);

        personMedicalRecord3.setFirstName("NearlyChild");
        personMedicalRecord3.setLastName("Family11");
        personMedicalRecord3.setAddress("address11");
        personMedicalRecord3.setPhone("phone1" );
        personMedicalRecord3.setEmail("mail.test1@email.com");
        personMedicalRecord3.setAge(19);

        personMedicalRecord4.setFirstName("Mum");
        personMedicalRecord4.setLastName("Family11");
        personMedicalRecord4.setAddress("address11");
        personMedicalRecord4.setPhone("phone4" );
        personMedicalRecord4.setEmail("mail.test4@email.com");
        personMedicalRecord4.setAge(40);

        personMedicalRecord5.setFirstName("Mum");
        personMedicalRecord5.setLastName("Family12");
        personMedicalRecord5.setAddress("address12");
        personMedicalRecord5.setPhone("phone5" );
        personMedicalRecord5.setEmail("mail.test5@email.com");
        personMedicalRecord5.setAge(50);

        personMedicalRecord6.setFirstName("Babe");
        personMedicalRecord6.setLastName("Family12");
        personMedicalRecord6.setAddress("address12");
        personMedicalRecord6.setPhone("phone6" );
        personMedicalRecord6.setEmail("mail.test6@email.com");
        personMedicalRecord6.setAge(0);

    }

    @Test
    public void getPersonAndCountByFireStationTest() {
        //GIVEN
        when(fireStationService.getAddresses(1)).thenReturn(Arrays.asList("address11","address12"));
        when(alertListsService.getMedicalRecordByAddress("address11", true))
                .thenReturn(Arrays.asList(personMedicalRecord1, personMedicalRecord2,
                        personMedicalRecord3, personMedicalRecord4));
        when(alertListsService.getMedicalRecordByAddress("address12", true))
                .thenReturn(Arrays.asList(personMedicalRecord5, personMedicalRecord6));

        //WHEN
        List<PersonAndCountByFireStationDTO> persons =
                personAndCountByFireStationService.getPersonAndCountByFireStation(1);

        //THEN
        verify(fireStationService, times(1)).getAddresses(1);
        verify(alertListsService,times(1)).getMedicalRecordByAddress("address11",true);
        verify(alertListsService,times(1)).getMedicalRecordByAddress("address12",true);

        assertThat(persons.get(0).getChildrenCount()).isEqualTo(2L);
        assertThat(persons.get(0).getAdultsCount()).isEqualTo(4L);
        assertThat(persons.get(0).getPersonsByFireStation().size()).isEqualTo(6);
        assertThat(persons.get(0).getPersonsByFireStation().get(5).getPhone()).isEqualTo("phone6");


    }
}
