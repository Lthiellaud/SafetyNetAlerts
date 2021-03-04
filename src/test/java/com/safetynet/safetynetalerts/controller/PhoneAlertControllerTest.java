package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.PhoneAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PhoneAlertController.class)
public class PhoneAlertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneAlertService phoneAlertService;

    @Autowired
    private PhoneAlertController phoneAlertController;

   @Test
    public void getPhoneListTest() throws Exception {
        mockMvc.perform(get("/phoneAlert").param("firestation", "2"))
                .andExpect(status().isOk());
    }

}
