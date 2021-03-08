package com.safetynet.safetynetalerts.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PersonAndCountByFireStationControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPersonAndCountByFireStationControllerIT() throws Exception {
        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].childrenCount", equalTo(1)))
                .andExpect(jsonPath("$[0].adultsCount", equalTo(5)))
                .andExpect(jsonPath("$[0].personsByFireStation", hasSize(6)))
                .andExpect(jsonPath("$[0].personsByFireStation[*].lastName", everyItem(endsWith("-st1"))))
                .andExpect(jsonPath("$[0].personsByFireStation[*].address", everyItem(endsWith("-st1"))))
                .andExpect(jsonPath("$[0].personsByFireStation[*].phone", everyItem(endsWith("-st1"))))
                .andDo(print());
    }
}
