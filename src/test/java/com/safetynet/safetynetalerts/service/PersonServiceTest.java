package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.PersonEmailMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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
    private static Person person2FalseUpdate;
    private static PersonId personId;

    private Calendar calendar = Calendar.getInstance();

    @BeforeEach
    public void initDtoTest(){
        person1 = new Person();
        person2 = new Person();
        person2Update = new Person();
        person2FalseUpdate = new Person();
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
        person2Update.setCity("Paris");
        person2Update.setZip(75001);
        person2Update.setPhone("phone3" );
        person2Update.setEmail("mail.test3@email.com");

        person2FalseUpdate.setFirstName("Dad");
        person2FalseUpdate.setLastName("Family12");

    }

    @Test
    public void updatePersonTest() {
        //GIVEN
        Optional<Person> optionalPerson = Optional.of(person2);
        when(personRepository.findById(personId)).thenReturn(optionalPerson);
        when(personRepository.save(any(Person.class))).thenReturn(any(Person.class));

        //WHEN - all attributes of person2Update, except firstname and lastname have changed
        Optional<Person> person = personService.updatePerson(person2Update);

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(any(Person.class));
        assertThat(person.isPresent()).isTrue();
        assertThat(person.get().equals(person2Update)).isTrue();
    }

    @Test
    public void falseUpdatePersonTest() {
        //GIVEN
        Optional<Person> optionalPerson = Optional.of(person2);
        when(personRepository.findById(personId)).thenReturn(optionalPerson);
        when(personRepository.save(any(Person.class))).thenReturn(any(Person.class));

        //WHEN - all attributes of person2FalseUpdate are null, except firstname and lastname
        Optional<Person> person = personService.updatePerson(person2FalseUpdate);

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(any(Person.class));
        assertThat(person.isPresent()).isTrue();
        assertThat(person.get().equals(person2)).isTrue();

    }

    @Test
    public void tryToUpdatePersonWithNoNameTest() {
        //WHEN
        Optional<Person> person = personService.updatePerson(new Person());

        //THEN
        verify(personRepository, times(0)).findById(personId);
        verify(personRepository, times(0)).save(any(Person.class));
        assertThat(person.isPresent()).isFalse();

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
    public void saveExistingPersonTest() {
        //GIVEN
        Optional<Person> optionalPerson = Optional.of(person2);
        when(personRepository.findById(personId)).thenReturn(optionalPerson);

        //WHEN
        Optional<Person> person = personService.createPerson(person2);

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(0)).save(any(Person.class));
        assertThat(person.isPresent()).isFalse();
    }

    @Test
    public void tryToSavePersonWithNoNameTest() {
        //WHEN
        Optional<Person> person = personService.createPerson(new Person());

        //THEN
        verify(personRepository, times(0)).findById(any(PersonId.class));
        verify(personRepository, times(0)).save(any(Person.class));
        assertThat(person.isPresent()).isFalse();
    }

    @Test
    public void savePersonTest() {
        //GIVEN
        Optional<Person> optionalPerson = Optional.empty();
        when(personRepository.findById(personId)).thenReturn(optionalPerson);
        when(personRepository.save(person2)).thenReturn(person2);

        //WHEN
        Optional<Person> person = personService.createPerson(person2);

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository, times(1)).save(person2);
        assertThat(person.isPresent()).isTrue();
    }

    @Test
    public void getPersonIdTest(){
        //GIVEN
        Optional<Person> optionalPerson = Optional.of(person2);
        when(personRepository.findById(personId)).thenReturn(optionalPerson);

        //THEN
        assertThat(personService.getPersonId(personId)).isTrue();
        verify(personRepository,times(1)).findById(personId);
    }

    @Test
    public void savePersonListTest(){
        //GIVEN
        List<Person> persons = Arrays.asList(person1, person2);
        when(personRepository.saveAll(persons)).thenReturn(persons);

        //THEN
        assertThat(personService.savePersonList(persons)).containsExactly(person1, person2);
        verify(personRepository,times(1)).saveAll(persons);
    }

    @Test
    public void deletePersonTest(){
        //GIVEN
        when(personRepository.findById(personId)).thenReturn(Optional.of(person2));

        //WHEN
        personService.deletePerson("Dad", "Family12");

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository,times(1)).deleteById(personId);
    }

    @Test
    public void deletePersonWithNullPersonIdTest(){
       //WHEN
        personService.deletePerson(null, null);

        //THEN
        verify(personRepository,times(0)).deleteById(any(PersonId.class));
        verify(personRepository, times(0)).findById(any(PersonId.class));
    }

    @Test
    public void deleteNonExistingTest(){
        //GIVEN
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        //WHEN
        personService.deletePerson("Dad", "Family12");

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository,times(0)).deleteById(any(PersonId.class));
    }

    @Test
    public void getEmailListTest(){
        //GIVEN
        when(personRepository.findAllDistinctEmailByCity("Paris"))
                .thenReturn(new ArrayList<>());

        //WHEN
        personService.getEmailList("Paris");

        //THEN
        verify(personRepository,times(1)).findAllDistinctEmailByCity("Paris");
    }

    @Test
    public void getPhonesTest(){
        //GIVEN
        when(personRepository.findAllDistinctPhoneByAddressIsIn(anyList()))
                .thenReturn(new ArrayList<>());

        //WHEN
        personService.getPhones(new ArrayList<>());

        //THEN
        verify(personRepository,times(1))
                .findAllDistinctPhoneByAddressIsIn(anyList());
    }

    @Test
    public void getAllByAddressTest(){
        //GIVEN
        when(personRepository.findAllByAddress("address 1")).thenReturn(new ArrayList<>());

        //WHEN
        personService.getAllByAddress("address 1");

        //THEN
        verify(personRepository,times(1)).findAllByAddress("address 1");
    }

    @Test
    public void getAllByFirstAndLastName_firstNameGiven_shouldReturn_onePerson() {
        //GIVEN
        PersonEmailMedicalRecordDTO p1 = new PersonEmailMedicalRecordDTO(person1);
        PersonEmailMedicalRecordDTO p2 = new PersonEmailMedicalRecordDTO(person2);
        List<PersonEmailMedicalRecordDTO> persons = Arrays.asList(p1, p2);
        when(personRepository.findAllByLastName("Family12")).thenReturn(persons);

        //WHEN
        List<PersonEmailMedicalRecordDTO> result =
                personService.getAllByFirstAndLastName("Baby", "Family12");

        //THEN
        verify(personRepository, times(1)).findAllByLastName("Family12");
        assertThat(result).containsExactly(p1);
    }
    @Test
    public void getAllByFirstAndLastName_NullFirstNameGiven_shouldReturn_onePerson() {
        //GIVEN
        PersonEmailMedicalRecordDTO p1 = new PersonEmailMedicalRecordDTO(person1);
        PersonEmailMedicalRecordDTO p2 = new PersonEmailMedicalRecordDTO(person2);
        List<PersonEmailMedicalRecordDTO> persons = Arrays.asList(p1, p2);
        when(personRepository.findAllByLastName("Family12")).thenReturn(persons);

        //WHEN
        List<PersonEmailMedicalRecordDTO> result =
                personService.getAllByFirstAndLastName(null, "Family12");

        //THEN
        verify(personRepository, times(1)).findAllByLastName("Family12");
        assertThat(result).containsExactlyInAnyOrder(p1, p2);
    }

    @Test
    public void getAllByFirstAndLastName_EmptyFirstNameGiven_shouldReturn_onePerson() {
        //GIVEN
        PersonEmailMedicalRecordDTO p1 = new PersonEmailMedicalRecordDTO(person1);
        PersonEmailMedicalRecordDTO p2 = new PersonEmailMedicalRecordDTO(person2);
        List<PersonEmailMedicalRecordDTO> persons = Arrays.asList(p1, p2);
        when(personRepository.findAllByLastName("Family12")).thenReturn(persons);

        //WHEN
        List<PersonEmailMedicalRecordDTO> result =
                personService.getAllByFirstAndLastName("", "Family12");

        //THEN
        verify(personRepository, times(1)).findAllByLastName("Family12");
        assertThat(result).containsExactlyInAnyOrder(p1, p2);
    }

    @Test
    public void getAllByFirstAndLastName_NullLastNameGiven_shouldReturn_onePerson() {
        //GIVEN
        PersonEmailMedicalRecordDTO p1 = new PersonEmailMedicalRecordDTO(person1);
        PersonEmailMedicalRecordDTO p2 = new PersonEmailMedicalRecordDTO(person2);
        List<PersonEmailMedicalRecordDTO> persons = Arrays.asList(p1, p2);
        when(personRepository.findAllByLastName("Family12")).thenReturn(persons);

        //WHEN
        List<PersonEmailMedicalRecordDTO> result =
                personService.getAllByFirstAndLastName("Baby", null);

        //THEN
        verify(personRepository, times(0)).findAllByLastName(anyString());
        assertThat(result).isNullOrEmpty();
    }

}
