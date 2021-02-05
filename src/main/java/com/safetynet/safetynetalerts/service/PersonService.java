package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * To get a person from his firstname and lastname.
     * @param personId defines firstname and lastname of the person
     * @return the founded person
     */
    public Optional<Person> getPerson(PersonId personId){
        return personRepository.findById(personId);
    }

    /**
     * To add a person.
     * @param person the person to be added
     * @return the saved person
     */
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    /**
     * To add a list of persons.
     * @param persons the list of persons to be added
     * @return the saved person
     */
    public Iterable<Person> savePersonList(List<Person> persons) {
        return personRepository.saveAll(persons);
    }

    /**
     * To delete a person from his firstname and lastname.
     * @param personId defines firstname and lastname of the person
     */
    public void deletePerson(PersonId personId) {
        personRepository.deleteById(personId);
    }

}
