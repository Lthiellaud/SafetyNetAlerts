package com.safetynet.safetynetalerts.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FloodControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getFloodListIT() throws Exception {
        mockMvc.perform(get("/flood/stations").param("stations", "1,2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].station", is(1)))
                .andExpect(jsonPath("$[1].station", is(2)))
                .andExpect(jsonPath("$[0].addresses[*].address", containsInAnyOrder("address1-st1", "address2-st1", "address3-st1")))
                .andExpect(jsonPath("$[1].addresses[0].persons[0].lastName", is("Marrack")))
                .andDo(print());
    }
}
