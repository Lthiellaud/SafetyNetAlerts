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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("address", is("71 Test St")))
                .andExpect(jsonPath("station", is(58000)));

        assertThat(fireStationRepository.findByAddress("71 Test St").get(0).getStation()).isEqualTo(58000);
    }

    @Test
    public void postExistingFireStationTest() throws Exception {
        fireStation = new FireStation();
        fireStation.setStation(1);
        fireStation.setAddress("address1-st1");
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));

        mockMvc.perform(createRequest)
                .andExpect(status().isConflict())
                .andExpect(content().string(""));

    }

    @Test
    public void postFireStationWithIdTest() throws Exception {
        fireStation = new FireStation();
        fireStation.setId(100L);
        fireStation.setStation(40000);
        fireStation.setAddress("address new");
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));

        mockMvc.perform(createRequest)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));

    }

    @Test
    public void postFireStationWithNoAddress() throws Exception {
        fireStation = new FireStation();
        fireStation.setStation(40000);
        fireStation.setAddress("");
        System.out.println(fireStation);
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));

        mockMvc.perform(createRequest)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));

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

        assertThat(fireStationRepository.findByAddress("Station to be updated-A1")
                .size()).isEqualTo(2);
        assertThat(fireStationRepository.findByAddress("Station to be updated-A1")
                .get(0).getStation()).isEqualTo(10);
        assertThat(fireStationRepository.findByAddress("Station to be updated-A1")
                .get(1).getStation()).isEqualTo(10);

    }

    @Test
    public void updateNonExistingFireStationTest() throws Exception {
        RequestBuilder Request = MockMvcRequestBuilders
                .put("/firestation/Unknown address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(10));

        mockMvc.perform(Request)
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        assertThat(fireStationRepository.findByAddress("Station to be updated-A1")
                .size()).isEqualTo(2);
        assertThat(fireStationRepository.findByAddress("Station to be updated-A1")
                .get(0).getStation()).isEqualTo(10);
        assertThat(fireStationRepository.findByAddress("Station to be updated-A1")
                .get(1).getStation()).isEqualTo(10);

    }

    @Test
    public void deleteFireStationByAddressTest() throws Exception {

        mockMvc.perform(delete("/firestation/address=Address to be deleted"))
                .andExpect(status().isNoContent());

        assertThat(fireStationRepository.findByAddress("Address to be deleted")).isEmpty();

    }

    @Test
    public void deleteFireStationByAddress_NotFoundTest() throws Exception {

        mockMvc.perform(delete("/firestation/address=Unknown address"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFireStationByAddress_BadRequestTest() throws Exception {

        mockMvc.perform(delete("/firestation/address="))
                .andExpect(status().isBadRequest());


    }

    @Test
    public void deleteFireStationByStationTest() throws Exception {

        mockMvc.perform(delete("/firestation/station=80"))
                .andExpect(status().isNoContent());

        assertThat(fireStationRepository.findByStation(80)).isEmpty();
    }

    @Test
    public void deleteFireStationByStation_NotFoundTest() throws Exception {

        mockMvc.perform(delete("/firestation/station=800"))
                .andExpect(status().isNotFound());

        assertThat(fireStationRepository.findByStation(800)).isEmpty();
    }

}

