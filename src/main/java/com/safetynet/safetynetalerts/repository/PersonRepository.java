package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository <Person, PersonId> {

    //List<Person> findAllDistinctByAddressIsIn(Iterable<String> addresses);
    //<T> List<T> findByStation(Integer station, Class<T> type);

    List<IPersonPhone> findAllDistinctPhoneByAddressIsIn(Iterable<String> addresses);
    List<IPersonEmail> findAllDistinctEmailByCity(String city);
    List<PersonDto> findAllByAddress(String address);
    //<T> List<T> findAllByAddress(String address, Class<T> type);
}
