package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.IPersonPhone;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository <Person, PersonId> {

    //List<Person> findAllDistinctByAddressIsIn(Iterable<String> addresses);
    //<T> List<T> findByStation(Integer station, Class<T> type);

    List<IPersonPhone> findAllDistinctPhoneByAddressIsIn(Iterable<String> addresses);
}
