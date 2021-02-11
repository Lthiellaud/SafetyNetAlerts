package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.service.PersonListsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = PersonListsController.class)
public class PersonListsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonListsService personListsService;

    @Autowired
    private PersonListsController personListsController;

    @Test
    public void getEmailsTest() throws Exception {
        mockMvc.perform(get("/communityEmail").param("city", "City"))
                .andExpect(status().isOk());
    }
}
