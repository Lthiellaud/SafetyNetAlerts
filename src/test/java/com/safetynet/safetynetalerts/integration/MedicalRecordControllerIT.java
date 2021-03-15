package com.safetynet.safetynetalerts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import static java.util.Calendar.JANUARY;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    private MedicalRecord medicalRecord;
    private Calendar calendar = Calendar.getInstance();

    private PersonId personBabyId = new PersonId("Baby", "Boyd");
    private PersonId personUpdateId = new PersonId("Update", "Boyd");
    private PersonId personExistingId = new PersonId("Existing", "Boyd");
    private PersonId personDeleteId = new PersonId("Delete", "Boyd");

    @BeforeEach
    public void setUpTest() {
        medicalRecord = new MedicalRecord();

    }
    @Test
    public void createMedicalRecordTest() throws Exception {
        calendar.set(2021,JANUARY, 17);
        medicalRecord.setFirstName("Baby");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate(calendar.getTime());
        medicalRecord.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
        medicalRecord.setAllergies(Arrays.asList("peanut", "shellfish"));
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));

        mockMvc.perform(createRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("firstName", is("Baby")))
                .andExpect(jsonPath("birthdate", is("01/17/2021")))
                .andExpect(jsonPath("medications[0]", is("aznol:350mg")))
                .andExpect(jsonPath("allergies[0]", is("peanut")));
    }

    @Test
    public void updateMedicalRecordTest() throws Exception {
        medicalRecord = medicalRecordRepository.findById(personUpdateId).get();
        medicalRecord.setMedications(Arrays.asList("hydrapermazol:100mg"));
        medicalRecord.setAllergies(Collections.emptyList());
        RequestBuilder request = MockMvcRequestBuilders
                .put("/medicalRecord/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("Update")))
                .andExpect(jsonPath("birthdate", is("03/06/1984")))
                .andExpect(jsonPath("medications[0]", is("hydrapermazol:100mg")))
                .andExpect(content().string(containsString("\"allergies\" : [ ]")))
        ;

    }

    @Test
    public void deleteMedicalRecordTest() throws Exception {
        mockMvc.perform(delete("/medicalRecord/Delete:Boyd"))
                .andExpect(status().isNoContent());

    }

}
