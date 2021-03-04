package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.*;
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
        assertThat(person).isEqualTo(Optional.empty());
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
        boolean response = personService.deletePerson("Dad", "Family12");

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository,times(1)).deleteById(personId);

        assertThat(response).isTrue();
    }

    @Test
    public void deleteNonExistingTest(){
        //GIVEN
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        //WHEN
        boolean response = personService.deletePerson("Dad", "Family12");

        //THEN
        verify(personRepository, times(1)).findById(personId);
        verify(personRepository,times(0)).deleteById(any(PersonId.class));
        assertThat(response).isFalse();
    }

    @Test
    public void getEmailListTest(){
        //GIVEN
        ICommunityEmailDTO email1 = () -> "email1";
        ICommunityEmailDTO email2 = () -> "email2";
        List<ICommunityEmailDTO> emails = Arrays.asList(email1, email2);
        when(personRepository.findAllDistinctEmailByCity("Culver"))
                .thenReturn(emails);

        //WHEN
        List<ICommunityEmailDTO> emailDTOList = personService.getEmailList("Culver");

        //THEN
        verify(personRepository,times(1)).findAllDistinctEmailByCity("Culver");
        assertThat(emailDTOList).containsExactlyInAnyOrder(email1, email2);
    }

    @Test
    public void getEmailList_nothingFoundTest(){
        //GIVEN
        when(personRepository.findAllDistinctEmailByCity("Paris"))
                .thenReturn(new ArrayList<>());

        //WHEN
        List<ICommunityEmailDTO> emailDTOList = personService.getEmailList("Paris");

        //THEN
        verify(personRepository,times(1)).findAllDistinctEmailByCity("Paris");
        assertThat(emailDTOList.size()).isEqualTo(0);
    }

    @Test
    public void getPhonesTest(){
        //GIVEN
        List<String> addresses = Arrays.asList("address12", "address3");
        IPhoneAlertDTO phone1 = () -> "phone1";
        IPhoneAlertDTO phone2 = () -> "phone2";
        List<IPhoneAlertDTO> phones = Arrays.asList(phone1, phone2);
        when(personRepository.findAllDistinctPhoneByAddressIsIn(addresses))
                .thenReturn(phones);

        //WHEN
        List<IPhoneAlertDTO> phonesFound = personService.getPhones(addresses);

        //THEN
        verify(personRepository,times(1))
                .findAllDistinctPhoneByAddressIsIn(anyList());
        assertThat(phonesFound).containsExactlyInAnyOrder(phone1, phone2);
    }

    @Test
    public void getPhones_nothingFoundTest(){
        //GIVEN
        List<String> addresses = Arrays.asList("address1", "address2");
        when(personRepository.findAllDistinctPhoneByAddressIsIn(addresses))
                .thenReturn(new ArrayList<>());

        //WHEN
        List<IPhoneAlertDTO> phones = personService.getPhones(new ArrayList<>());

        //THEN
        verify(personRepository,times(1))
                .findAllDistinctPhoneByAddressIsIn(anyList());
        assertThat(phones.size()).isEqualTo(0);
    }

    @Test
    public void getAllByAddressTest(){
        //GIVEN
        PersonMedicalRecordDTO p1 = new PersonMedicalRecordDTO(person1);
        PersonMedicalRecordDTO p2 = new PersonMedicalRecordDTO(person2);
        List<PersonMedicalRecordDTO> persons = Arrays.asList(p1, p2);
        when(personRepository.findAllByAddress("address12")).thenReturn(persons);

        //WHEN
        List<PersonMedicalRecordDTO> personsFound = personService.getAllByAddress("address12");

        //THEN
        verify(personRepository,times(1)).findAllByAddress("address12");
        assertThat(persons.size()).isEqualTo(2);
    }

    @Test
    public void getAllByAddress_nothingFoundTest(){
        //GIVEN
        when(personRepository.findAllByAddress("address new")).thenReturn(new ArrayList<>());

        //WHEN
        List<PersonMedicalRecordDTO> persons = personService.getAllByAddress("address new");

        //THEN
        verify(personRepository,times(1)).findAllByAddress("address new");
        assertThat(persons.size()).isEqualTo(0);
    }

    @Test
    public void getAllByFirstAndLastName_firstNameGiven_shouldReturn_onePerson() {
        //GIVEN
        PersonMedicalRecordDTO p1 = new PersonMedicalRecordDTO(person1);
        PersonMedicalRecordDTO p2 = new PersonMedicalRecordDTO(person2);
        List<PersonMedicalRecordDTO> persons = Arrays.asList(p1, p2);
        when(personRepository.findAllByLastName("Family12")).thenReturn(persons);

        //WHEN
        List<PersonMedicalRecordDTO> result =
                personService.getAllByFirstAndLastName("Baby", "Family12");

        //THEN
        verify(personRepository, times(1)).findAllByLastName("Family12");
        assertThat(result).containsExactly(p1);
    }
    @Test
    public void getAllByFirstAndLastName_NullFirstNameGiven_shouldReturn_twoPerson() {
        //GIVEN
        PersonMedicalRecordDTO p1 = new PersonMedicalRecordDTO(person1);
        PersonMedicalRecordDTO p2 = new PersonMedicalRecordDTO(person2);
        List<PersonMedicalRecordDTO> persons = Arrays.asList(p1, p2);
        when(personRepository.findAllByLastName("Family12")).thenReturn(persons);

        //WHEN
        List<PersonMedicalRecordDTO> result =
                personService.getAllByFirstAndLastName(null, "Family12");

        //THEN
        verify(personRepository, times(1)).findAllByLastName("Family12");
        assertThat(result).containsExactlyInAnyOrder(p1, p2);
    }

    @Test
    public void getAllByFirstAndLastName_EmptyFirstNameGiven_shouldReturn_twoPerson() {
        //GIVEN
        PersonMedicalRecordDTO p1 = new PersonMedicalRecordDTO(person1);
        PersonMedicalRecordDTO p2 = new PersonMedicalRecordDTO(person2);
        List<PersonMedicalRecordDTO> persons = Arrays.asList(p1, p2);
        when(personRepository.findAllByLastName("Family12")).thenReturn(persons);

        //WHEN
        List<PersonMedicalRecordDTO> result =
                personService.getAllByFirstAndLastName("", "Family12");

        //THEN
        verify(personRepository, times(1)).findAllByLastName("Family12");
        assertThat(result).containsExactlyInAnyOrder(p1, p2);
    }

    @Test
    public void getAllByFirstAndLastName_NullLastNameGiven_shouldReturnNothing() {
        //WHEN
        List<PersonMedicalRecordDTO> result =
                personService.getAllByFirstAndLastName("Baby", null);

        //THEN
        verify(personRepository, times(0)).findAllByLastName(anyString());
        assertThat(result).isNullOrEmpty();
    }

    @Test
    public void getAllByFirstAndLastName_unknownFirstNameGiven_shouldReturnNothing() {
        //GIVEN
        PersonMedicalRecordDTO p1 = new PersonMedicalRecordDTO(person1);
        PersonMedicalRecordDTO p2 = new PersonMedicalRecordDTO(person2);
        List<PersonMedicalRecordDTO> persons = Arrays.asList(p1, p2);
        when(personRepository.findAllByLastName("Family12")).thenReturn(persons);

        //WHEN
        List<PersonMedicalRecordDTO> result =
                personService.getAllByFirstAndLastName("Jason", "Family12");

        //THEN
        verify(personRepository, times(1)).findAllByLastName("Family12");
        assertThat(result).isNullOrEmpty();
    }

}
