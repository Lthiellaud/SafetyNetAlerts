package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.IPersonEmail;
import com.safetynet.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonListsService {

    @Autowired
    PersonRepository personRepository;

    public List<IPersonEmail> getEmailList(String city){
        return personRepository.findAllDistinctEmailByCity(city);
    }
}
