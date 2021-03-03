package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.util.DateUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static java.util.Calendar.JANUARY;
import static java.util.Calendar.MARCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "command.line.runner.enabled=false")
@ActiveProfiles("test")
public class PersonInfoServiceTest {

    @MockBean
    private PersonService personService;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PersonInfoService personInfoService;

    private static PersonId personId2;

    private static MedicalRecord medicalRecord2;

    private static PersonMedicalRecordDTO personMedicalRecord2;

    private static Date birthdate2;

    private static DateUtil dateUtil;


    private static Calendar calendar = Calendar.getInstance();

    @BeforeAll
    public static void init() {
        personId2 = new PersonId("Dad", "Family12");

        medicalRecord2 = new MedicalRecord();

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

        personMedicalRecord2 = new PersonMedicalRecordDTO();

        personMedicalRecord2.setFirstName("Dad");
        personMedicalRecord2.setLastName("Family12");
        personMedicalRecord2.setAddress("address12");
        personMedicalRecord2.setPhone("phone1" );
        personMedicalRecord2.setEmail("mail.test1@email.com");
    }

    @Test
    public void getPersonInfoTest() {
        //GIVEN
        List<PersonMedicalRecordDTO> personMedicalRecords = Arrays.asList(personMedicalRecord2);
        when(personService.getAllByFirstAndLastName("Dad", "Boyd")).thenReturn(personMedicalRecords);
        when(medicalRecordService.getMedicalRecord(personId2)).thenReturn(Optional.of(medicalRecord2));

        //WHEN
        List<PersonMedicalRecordDTO> persons = personInfoService.getPersonInfo("Dad", "Boyd");

        //THEN
        assertThat(persons.size()).isEqualTo(1);
        assertThat(persons.get(0).getAge()).isEqualTo(dateUtil.age(birthdate2));
        assertThat(persons.get(0).getAllergies()).containsExactly("allergie2");
        assertThat(persons.get(0).getMedications()).containsExactly("med1", "med2");
    }
}
