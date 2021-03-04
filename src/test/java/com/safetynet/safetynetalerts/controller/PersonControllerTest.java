package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import com.safetynet.safetynetalerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private PersonService personService;

    @Autowired
    private PersonController personController;

    private Person person;

    @BeforeEach
    public void setUpTest() {
        person = new Person();

    }
    @Test
    public void createExistingPersonTest() throws Exception {
        person.setFirstName("Existing");
        person.setLastName("Boyd");
        person.setAddress("1509 Marinland St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setPhone("841-874-0000" );
        person.setEmail("existing2boyd@email.com");
        RequestBuilder createRequest = MockMvcRequestBuilders
                .post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person));
        mockMvc.perform(createRequest)
                .andExpect(status().isConflict());
    }

    @Test
    public void updateNonExistingPersonTest() throws Exception {
        person.setFirstName("Unknown");
        person.setLastName("Boyd");
        person.setEmail("test.update@email.com");
        person.setPhone(null);
        person.setZip(null);
        person.setCity(null);
        person.setAddress("1508 Culver St");
        RequestBuilder request = MockMvcRequestBuilders
                .put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person));
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateEmptyNamePersonTest() throws Exception {
        person.setFirstName("");
        person.setLastName("");
        person.setEmail("test.update@email.com");
        person.setPhone(null);
        person.setZip(null);
        person.setCity(null);
        person.setAddress("1508 Culver St");
        RequestBuilder request = MockMvcRequestBuilders
                .put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNonExistingPersonTest() throws Exception {
        //deleting M. NonExisting Boyd
        mockMvc.perform(delete("/person/NonExisting:Boyd"))
                .andExpect(status().isNotFound());

    }
    @Test
    public void deleteBadRequestPersonTest() throws Exception {
        mockMvc.perform(delete("/person/John:"))
                .andExpect(status().isBadRequest());

    }
}
