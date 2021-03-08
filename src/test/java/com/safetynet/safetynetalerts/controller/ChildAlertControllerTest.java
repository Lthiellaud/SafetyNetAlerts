package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.ChildAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ChildAlertController.class)
public class ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildAlertService childAlertService;

    @Test
    public void childAlertControllerTest() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", "given address"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getChildList_noParamIT() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", ""))
                .andExpect(status().isBadRequest());
    }
}
