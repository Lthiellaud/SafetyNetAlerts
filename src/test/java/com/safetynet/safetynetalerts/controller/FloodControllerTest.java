package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.AlertListsService;
import com.safetynet.safetynetalerts.service.FloodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FloodController.class)
public class FloodControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FloodService floodService;

    @Autowired
    private FloodController floodController;

    @Test
    public void getFloodListTest() throws Exception {
        mockMvc.perform(get("/flood/stations").param("stations", "1,2"))
                .andExpect(status().isNotFound());
    }

}
