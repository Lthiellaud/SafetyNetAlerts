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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FireControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getFirePersonsForAddress_shouldReturnData() throws Exception {
        mockMvc.perform(get("/fire").param("address", "address3-st1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("[ 1 ]")))
                .andExpect(content().string(containsString("Family3-st1")));
    }

    @Test
    public void getFirePersonsForNewAddress_shouldReturnEmptyArray() throws Exception {
        mockMvc.perform(get("/fire").param("address", "new address"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("[ ]"));
    }

    @Test
    public void getFirePersonsForNullAddress_shouldReturnNull() throws Exception {
        mockMvc.perform(get("/fire").param("address", ""))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(""));
    }

}
