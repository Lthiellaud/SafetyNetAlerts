package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "command.line.runner.enabled=false")
public class PersonServiceTest {
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private static Person person1;
    private static Person person2;
    private static Person person2Update;
    private static Person person4;
    private static PersonId personId;

    private Calendar calendar = Calendar.getInstance();

    @BeforeEach
    public void initDtoTest(){
        person1 = new Person();
        person2 = new Person();
        person2Update = new Person();
        person4 = new Person();
        personId = new PersonId("Dad", "Family12");

        person1.setFirstName("Baby");
        person1.setLastName("Family12");
        person1.setAddress("address12");
        person1.setCity("Culver");
        person1.setZip(97451);
        person1.setPhone("phone1" );
        person1.setEmail("mail.test1@email.com");

        person2.setFirstName("Dad");
        person2.setLastName("Family12");
        person2.setAddress("address12");
        person2.setCity("Culver");
        person2.setZip(97458);
        person2.setPhone("phone1" );
        person2.setEmail("mail.test1@email.com");

        person2Update.setFirstName("Dad");
        person2Update.setLastName("Family12");
        person2Update.setAddress("address3");
        person2Update.setPhone("phone3" );
        person2Update.setEmail("mail.test3@email.com");

        person4.setFirstName("Suzan");
        person4.setLastName("Family4");
        person4.setAddress("address4");
        person4.setCity("Culver");
        person4.setZip(97458);
        person4.setPhone("phone4" );
        person4.setEmail("mail.test4@email.com");

    }

    @Test
    public void updatePersonTest() {
        //GIVEN
        Optional<Person> optionalPerson = Optional.of(person2);
        when(personRepository.findById(personId)).thenReturn(optionalPerson);
        when(personRepository.save(any(Person.class))).thenReturn(any(Person.class));

        //WHEN
        Optional<Person> person = personService.updatePerson(person2Update);

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(any(Person.class));
        assertThat(person.isPresent()).isTrue();
        assertThat(person.get().getAddress()).isEqualTo("address3");
        assertThat(person.get().getCity()).isEqualTo("Culver");
        assertThat(person.get().getZip()).isEqualTo(97458);
        assertThat(person.get().getPhone()).isEqualTo("phone3");
        assertThat(person.get().getEmail()).isEqualTo("mail.test3@email.com");

    }

    @Test
    public void updateNonExistingPersonTest() {
        //GIVEN
        Optional<Person> optionalPerson = Optional.empty();
        when(personRepository.findById(personId)).thenReturn(optionalPerson);

        //WHEN
        Optional<Person> person = personService.updatePerson(person2Update);

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(0)).save(any(Person.class));
        assertThat(person.isPresent()).isFalse();

    }

    @Test
    public void savePersonTest() {
        //GIVEN
        when(personRepository.save(any(Person.class))).thenReturn(person1);

        //WHEN
        personService.savePerson(person1);

        //THEN
        verify(personRepository, times(1)).save(person1);
    }

    @Test
    public void getAllByFirstAndLastName_firstNameGiven_shouldReturn_onePerson() {
        when(personRepository.findAllByLastName("Family12")).thenReturn(anyList());
    }

}
