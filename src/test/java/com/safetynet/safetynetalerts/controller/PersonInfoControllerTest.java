package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.PersonInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonInfoController.class)
public class PersonInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonInfoService personInfoService;

    @Test
    public void getPersonInfoControllerTest() throws Exception {
        mockMvc.perform(get("/personInfo").param("firstName", "Jean")
                .param("lastName", "JeanJean"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPersonInfoController_withNoLastNameTest() throws Exception {
        mockMvc.perform(get("/personInfo").param("firstName", "Jean")
                .param("lastName", ""))
                .andExpect(status().isBadRequest());
    }

}
