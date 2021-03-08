package com.safetynet.safetynetalerts.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PhoneAlertControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPhoneAlert_station1_shouldReturnPhoneNumber() throws Exception {
        mockMvc.perform(get("/phoneAlert").param("firestation", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("\"phone\" : \"phone3-st1\"")))
                .andExpect(content().string(containsString("\"phone\" : \"phone2-1-st1\"")))
                .andExpect(content().string(containsString("\"phone\" : \"phone2-2-st1\"")))
                .andExpect(content().string(containsString("\"phone\" : \"phone1-st1\"")));
    }

}

