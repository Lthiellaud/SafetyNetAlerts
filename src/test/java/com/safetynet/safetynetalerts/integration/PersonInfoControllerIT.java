package com.safetynet.safetynetalerts.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PersonInfoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPersonInfoControllerIT() throws Exception {
        mockMvc.perform(get("/personInfo")
                .param("firstName", "Peter")
                .param("lastName", "Family1-st1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].phone").doesNotExist())
                .andExpect(jsonPath("$[0].email", is("email1.test1-1d@email.com") ))
                .andExpect(jsonPath("$[0].medications", empty()))
                .andExpect(jsonPath("$[0].allergies", contains("shellfish")))
                .andDo(print());

    }

    @Test
    public void getPersonInfoController_lastNameOnlyIT() throws Exception {
        mockMvc.perform(get("/personInfo")
                .param("firstName", "")
                .param("lastName", "Cooper"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].phone").doesNotExist())
                .andExpect(jsonPath("$[*].email", containsInAnyOrder("tcoop@ymail.com", "mcoop@ymail.com") ))
                .andExpect(jsonPath("$[*].firstName", containsInAnyOrder("Marty", "Tony")))
                .andExpect(jsonPath("$[*].address", containsInAnyOrder("Address attached to 2 fire station", "Address Cooper 2")))
               .andDo(print());

    }


}

