package com.safetynet.safetynetalerts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FireStationListsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPhoneAlert_station6_shouldReturnEmptyBody() throws Exception {
        mockMvc.perform(get("/phoneAlert").param("firestation", "6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("phone").doesNotExist());
    }

    @Test
    public void getPhoneAlert_station1_shouldReturnPhoneNumber() throws Exception {
        mockMvc.perform(get("/phoneAlert").param("firestation", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"phone\":\"phone3-1\"")))
                .andExpect(content().string(containsString("\"phone\":\"phone2-1-1\"")))
                .andExpect(content().string(containsString("\"phone\":\"phone2-1-2\"")))
                .andExpect(content().string(containsString("\"phone\":\"phone1-1\"")));
    }

}

