package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.FireStationService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.AlertListsService;
import com.safetynet.safetynetalerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = AlertListsController.class)
public class AlertListsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertListsService alertListsService;

    @MockBean
    private PersonService personService;

    @MockBean
    private FireStationService fireStationService;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private AlertListsController alertListsController;

    @Test
    public void getEmailsTest() throws Exception {
        mockMvc.perform(get("/communityEmail").param("city", "City"))
                .andExpect(status().isOk());
    }
    @Test
    public void getFirePersonsTest() throws Exception {
        mockMvc.perform(get("/fire").param("address", "Given Address"))
                .andExpect(status().isOk());
    }
}
