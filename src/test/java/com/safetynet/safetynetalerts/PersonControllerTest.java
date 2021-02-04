package com.safetynet.safetynetalerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    private static Person person;

    @BeforeEach
    public void setUpTest() {
        person = new Person();
        person.setFirstName("Baby");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setPhone("841-874-6512" );
        person.setEmail("jaboyd@email.com");
    }
    @Test
    public void createPersonTest() throws Exception {
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person));
        mockMvc.perform(createRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("Baby")))
                .andExpect(jsonPath("address", is("1509 Culver St")))
                .andExpect(jsonPath("email", is("jaboyd@email.com")));
    }

    @Test
    public void updatePersonTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person));
        mockMvc.perform(request);
        person.setEmail("test.update@email.com");
        person.setPhone(null);
        person.setZip(null);
        person.setCity(null);
        person.setAddress("1508 Culver St");
        request = MockMvcRequestBuilders
                .put("/person/Baby:Boyd")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName", is("Baby")))
                .andExpect(jsonPath("city", is("Culver")))
                .andExpect(jsonPath("address", is("1508 Culver St")))
                .andExpect(jsonPath("email", is("test.update@email.com")));
    }

    @Test
    public void deletePersonTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person));
        mockMvc.perform(request);
        mockMvc.perform(delete("/person/Baby:Boyd"))
                .andExpect(status().isOk());
    }
}
