package com.safetynet.safetynetalerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    private FireStation fireStation;

    @BeforeEach
    public void setUpTest() {
        fireStation = new FireStation();
        fireStation.setStation(1);
        fireStation.setAddress("1509 Culver St");
    }

    @Test
    public void postFireStationTest() throws Exception {
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));

        mockMvc.perform(createRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("1509 Culver St")))
                .andExpect(jsonPath("station", is(1)));
    }

    @Test
    public void updateFireStationTest() throws Exception {
        RequestBuilder Request = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));
        mockMvc.perform(Request);
        Request = MockMvcRequestBuilders
                .put("/firestation/1509 Culver St")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(2));
        mockMvc.perform(Request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("address", is("1509 Culver St")))
                .andExpect(jsonPath("station", is(2)));
    }

    @Test
    public void deleteFireStationByAddressTest() throws Exception {
        RequestBuilder Request = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));
        mockMvc.perform(Request);
        fireStation.setStation(1);
        fireStation.setAddress("999 Test St");
        Request = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));
        mockMvc.perform(Request);
        mockMvc.perform(delete("/firestation/address=1509 Culver St"))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteFireStationByAddressStation() throws Exception {
        RequestBuilder Request = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));
        mockMvc.perform(Request);
        fireStation.setStation(2);
        fireStation.setAddress("999 Test St");
        Request = MockMvcRequestBuilders
                .post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fireStation));
        mockMvc.perform(Request);
        mockMvc.perform(delete("/firestation/station=1"))
                .andExpect(status().isOk());

    }

}

