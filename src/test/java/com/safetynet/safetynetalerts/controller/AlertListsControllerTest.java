package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.AlertListsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlertListsController.class)
public class AlertListsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertListsService alertListsService;

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
    @Test
    public void getPhoneListTest() throws Exception {
        mockMvc.perform(get("/phoneAlert").param("firestation", "2"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonInfoListTest() throws Exception {
        mockMvc.perform(get("/personInfo").param("firstName", "Jean")
                .param("lastName", "JeanJean"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFloodListTest() throws Exception {
        mockMvc.perform(get("/flood/stations").param("stations", "1,2"))
                .andExpect(status().isOk());
    }
}
