package com.safetynet.safetynetalerts.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.repository.FireStationRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FireStationControllerIT {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private FireStationRepository fireStationRepository;
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("71 Test St")))
                .andExpect(jsonPath("station", is(58000)));

        assertThat(fireStationRepository.findByAddress("71 Test St").get(0).getStation()).isEqualTo(58000);
    }

    @Test
    public void updateFireStationTest() throws Exception {
        RequestBuilder Request = MockMvcRequestBuilders
                .put("/firestation/Address attached to 2 fire station")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(5));
        //2 fire stations for address 112 Steppes Pl : the request returns 2 fire stations (0&1)
        mockMvc.perform(Request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].address", is("Address attached to 2 fire station")))
                .andExpect(jsonPath("$[1].station", is(5)));

        assertThat(fireStationRepository.findByAddress("Address attached to 2 fire station").size()).isEqualTo(2);
        assertThat(fireStationRepository.findByAddress("Address attached to 2 fire station").get(0).getStation()).isEqualTo(5);
        assertThat(fireStationRepository.findByAddress("Address attached to 2 fire station").get(1).getStation()).isEqualTo(5);

    }

    @Test
    public void deleteFireStationByAddressTest() throws Exception {

        mockMvc.perform(delete("/firestation/address=748 Townings Dr"))
                .andExpect(status().isOk());

        assertThat(fireStationRepository.findByAddress("748 Townings Dr")).isEmpty();

    }
    @Test
    public void deleteFireStationByStation() throws Exception {

        mockMvc.perform(delete("/firestation/station=2"))
                .andExpect(status().isOk());

        assertThat(fireStationRepository.findByStation(2)).isEmpty();
    }

}

