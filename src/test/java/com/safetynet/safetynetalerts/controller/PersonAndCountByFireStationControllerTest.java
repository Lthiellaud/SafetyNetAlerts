package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.PersonAndCountByFireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonAndCountByFireStationController.class)
public class PersonAndCountByFireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonAndCountByFireStationService personAndCountByFireStationService;

    @Test
    public void getPersonAndCountByFireStationControllerTest() throws Exception {
        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isNotFound());
    }


}
