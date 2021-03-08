package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import static java.util.Calendar.JANUARY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    MedicalRecordService medicalRecordService;

    private ObjectMapper mapper = new ObjectMapper();

    private MedicalRecord medicalRecord;
    private Calendar calendar = Calendar.getInstance();

    @BeforeEach
    public void setUpTest() {
        medicalRecord = new MedicalRecord();

    }
    @Test
    public void CreateExistingMedicalRecordTest() throws Exception {
        //as MedicalRecordService is mocked, the method createMedicalRecord sends an empty optional
        //as if the record for which we ask a creation was already existing => Conflict response
        calendar.set(2021,JANUARY, 17);
        medicalRecord.setFirstName("Existing");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate(calendar.getTime());
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));

        mockMvc.perform(createRequest)
                .andExpect(status().isConflict());

    }

    @Test
    public void CreateMedicalRecordWithNoNameTest() throws Exception {
        calendar.set(2021,JANUARY, 17);
        medicalRecord.setFirstName("");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate(calendar.getTime());
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));

        mockMvc.perform(createRequest)
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateNonExistingMedicalRecordTest() throws Exception {
        medicalRecord.setFirstName("NonExisting");
        medicalRecord.setLastName("NonExisting");
        RequestBuilder request = MockMvcRequestBuilders
                .put("/medicalRecord/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));
        mockMvc.perform(request)
                .andExpect(status().isNotFound());


    }

    @Test
    public void updateMedicalRecordWithNoNameTest() throws Exception {
        medicalRecord.setFirstName("");
        medicalRecord.setLastName("");
        RequestBuilder request = MockMvcRequestBuilders
                .put("/medicalRecord/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    @Test
    public void deleteNonExistingMedicalRecordTest() throws Exception {
        mockMvc.perform(delete("/medicalRecord/NonExisting:Boyd"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMedicalRecordWithNoNameTest() throws Exception {
        mockMvc.perform(delete("/medicalRecord/John:"))
                .andExpect(status().isBadRequest());
    }
}
