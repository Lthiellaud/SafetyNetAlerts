package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Optional<Person> getPerson(PersonId personId){
        return personRepository.findById(personId);
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public void deletePerson(PersonId personId) {
        personRepository.deleteById(personId);
    }

}
