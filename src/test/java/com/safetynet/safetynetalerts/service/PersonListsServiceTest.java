package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.IPersonEmail;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = "command.line.runner.enabled=false")
@ActiveProfiles("test")
public class PersonListsServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private static PersonListsService personListsService;

    @Test
    public void getEmailListTest(){
        //GIVEN
        when(personRepository.findAllDistinctEmailByCity("City")).thenReturn(anyList());

        //WHEN
        personListsService.getEmailList("City");

        //THEN
        verify(personRepository, times(1)).findAllDistinctEmailByCity("City");

    }
}
