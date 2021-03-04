package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Autowired
    private FireStationController fireStationController;

    private FireStation fireStation;
    private ObjectMapper mapper = new ObjectMapper();

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
                .andExpect(status().isConflict());

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
                .andExpect(status().isBadRequest());

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
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateNonExistingFireStationTest() throws Exception {
        RequestBuilder Request = MockMvcRequestBuilders
                .put("/firestation/Unknown address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(10));

        mockMvc.perform(Request)
                .andExpect(status().isNotFound());

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
    public void deleteFireStationByStation_NotFoundTest() throws Exception {

        mockMvc.perform(delete("/firestation/station=800"))
                .andExpect(status().isNotFound());
    }

}

