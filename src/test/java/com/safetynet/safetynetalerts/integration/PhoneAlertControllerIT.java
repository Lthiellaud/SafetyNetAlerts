package com.safetynet.safetynetalerts.integration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PhoneAlertControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getEmailsForCityCity_shouldReturnEmptyBody() throws Exception {
        mockMvc.perform(get("/communityEmail").param("city", "City"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("email").doesNotExist());
    }

    @Test
    @Disabled
    public void getFirePersonsForAddress_shouldReturnData() throws Exception {
        mockMvc.perform(get("/fire").param("address", "address3-1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getFirePersonsForGivenAddress_shouldReturnEmptyBody() throws Exception {
        mockMvc.perform(get("/fire").param("address", "Given Address"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("firstName").doesNotExist());
    }



}

