package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.model.DTO.ICommunityEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.IPhoneAlertDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import com.safetynet.safetynetalerts.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class PersonRepositoryIT {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void findAllDistinctPhoneByAddressIsInTest() {
        List<IPhoneAlertDTO> iPhoneAlertDTOS = personRepository.findAllDistinctPhoneByAddressIsIn(
                Arrays.asList("address1-st1"));

        assertThat(iPhoneAlertDTOS.get(0).getPhone()).isEqualTo("phone1-st1");
    }

    @Test
    public void findAllDistinctEmailByCityTest() {

        List<ICommunityEmailDTO> iCommunityEmailDTOS = personRepository
                .findAllDistinctEmailByCity("Paris");

        assertThat(iCommunityEmailDTOS.get(0).getEmail()).isEqualTo("email.paris@email.com");
    }

    @Test
    public void findAllByAddressTest() {
       List<PersonMedicalRecordDTO> personMedicalRecordDTOS = personRepository
                .findAllByAddress("address2-st1");

        assertThat(personMedicalRecordDTOS.get(0).getFirstName()).isEqualTo("Jamie");
        assertThat(personMedicalRecordDTOS.get(0).getLastName()).isEqualTo("Family22-st1");
        assertThat(personMedicalRecordDTOS.get(0).getAddress()).isEqualTo("address2-st1");
        assertThat(personMedicalRecordDTOS.get(0).getPhone()).isEqualTo("phone2-2-st1");
        assertThat(personMedicalRecordDTOS.get(0).getEmail()).isEqualTo("email2.test21@email.com");
        assertThat(personMedicalRecordDTOS.get(0).getAge()).isEqualTo(0);
        assertThat(personMedicalRecordDTOS.get(0).getMedications()).isNull();
        assertThat(personMedicalRecordDTOS.get(0).getAllergies()).isNull();

    }


    @Test
    public void findAllByLastNameTest() {
       List<PersonMedicalRecordDTO> personMedicalRecordDTOS = personRepository
                .findAllByLastName("Family22-st1");

        assertThat(personMedicalRecordDTOS.get(0).getFirstName()).isEqualTo("Jamie");
        assertThat(personMedicalRecordDTOS.get(0).getLastName()).isEqualTo("Family22-st1");
        assertThat(personMedicalRecordDTOS.get(0).getAddress()).isEqualTo("address2-st1");
        assertThat(personMedicalRecordDTOS.get(0).getPhone()).isEqualTo("phone2-2-st1");
        assertThat(personMedicalRecordDTOS.get(0).getEmail()).isEqualTo("email2.test21@email.com");
        assertThat(personMedicalRecordDTOS.get(0).getAge()).isEqualTo(0);
        assertThat(personMedicalRecordDTOS.get(0).getMedications()).isNull();
        assertThat(personMedicalRecordDTOS.get(0).getAllergies()).isNull();
    }

}
