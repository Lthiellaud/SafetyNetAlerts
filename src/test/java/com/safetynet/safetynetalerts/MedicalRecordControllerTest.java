package com.safetynet.safetynetalerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    private MedicalRecord medicalRecord;
    private Calendar calendar = Calendar.getInstance();


    @BeforeEach
    public void setUpTest() {
        calendar.set(2021,calendar.JANUARY, 17);
        medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Baby");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate(calendar.getTime());
        medicalRecord.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
        medicalRecord.setAllergies(Arrays.asList("peanut", "shellfish"));
    }
    @Test
    public void createMedicalRecordTest() throws Exception {
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));

        mockMvc.perform(createRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("Baby")))
                .andExpect(jsonPath("birthdate", is("01/17/2021")))
                .andExpect(jsonPath("medications[0]", is("aznol:350mg")))
                .andExpect(jsonPath("allergies[0]", is("peanut")));
    }

    @Test
    public void updateMedicalRecordTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));
        mockMvc.perform(request);
        calendar.set(2021,calendar.JANUARY, 18);
        medicalRecord.setBirthdate(calendar.getTime());
        medicalRecord.setMedications(Arrays.asList("hydrapermazol:100mg"));
        medicalRecord.setAllergies(null);
        request = MockMvcRequestBuilders
                .put("/medicalRecord/Baby:Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("Baby")))
                .andExpect(jsonPath("birthdate", is("01/18/2021")))
                .andExpect(jsonPath("medications[0]", is("hydrapermazol:100mg")))
                .andExpect(jsonPath("allergies", nullValue()));
    }

    @Test
    public void deleteMedicalRecordTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicalRecord));
        mockMvc.perform(request);
        mockMvc.perform(delete("/medicalRecord/Baby:Boyd"))
                .andExpect(status().isOk());
    }
}
