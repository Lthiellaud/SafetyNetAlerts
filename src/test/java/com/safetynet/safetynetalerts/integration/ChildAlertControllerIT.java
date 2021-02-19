package com.safetynet.safetynetalerts.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ChildAlertControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getChildAlertListIT() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", "address3-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].children[0].firstName", containsString("Kendrik")))
                .andExpect(jsonPath("$[0].adults[1].firstName", containsString("Shawna")))
                .andDo(print());
    }

    @Test
    public void getEmptyChildListIT() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", "given address"))
                .andExpect(status().isOk())
                .andExpect(content().string("[ ]"))
                .andDo(print());
        //TODO: add other ChildAlertController Integration Test and check family above never modified by other tests
        //TODO: check omitted pretty JSON throw tests (cf. application-test.properties)
        //TODO: /firestation by station number list
        //TODO: check format list for child alert
    }
}
