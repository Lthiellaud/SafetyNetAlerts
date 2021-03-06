package com.safetynet.safetynetalerts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
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
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FireStationControllerIT {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    private FireStation fireStation;

    @Test
    public void postFireStationTest() throws Exception {
        fireStation = new FireStation();
        fireStation.setStation(58000);
        fireStation.setAddress("71 Test St");
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));

        mockMvc.perform(createRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("address", is("71 Test St")))
                .andExpect(jsonPath("station", is(58000)));

    }

    @Test
    public void updateFireStationTest() throws Exception {
        RequestBuilder Request = MockMvcRequestBuilders
                .put("/firestation/Station to be updated-A1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(10));
        //2 fire stations for address "Station to be updated-A1" : the request returns 2 fire stations (0&1)
        mockMvc.perform(Request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].address", is("Station to be updated-A1")))
                .andExpect(jsonPath("$[1].station", is(10)));

    }

    @Test
    public void deleteFireStationByAddressTest() throws Exception {

        mockMvc.perform(delete("/firestation?address=Address to be deleted"))
                .andExpect(status().isNoContent());

    }

    @Test
    public void deleteFireStationByStationTest() throws Exception {

        mockMvc.perform(delete("/firestation?station=80"))
                .andExpect(status().isNoContent());

    }

}

