package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.*;
import com.safetynet.safetynetalerts.model.DTO.IPersonEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.IPersonPhoneDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonEmailMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository <Person, PersonId> {

    //List<Person> findAllDistinctByAddressIsIn(Iterable<String> addresses);
    //<T> List<T> findByStation(Integer station, Class<T> type);

    List<IPersonPhoneDTO> findAllDistinctPhoneByAddressIsIn(Iterable<String> addresses);
    List<IPersonEmailDTO> findAllDistinctEmailByCity(String city);
    List<PersonPhoneMedicalRecordDTO> findAllByAddress(String address);
    List<PersonEmailMedicalRecordDTO> findAllByFirstNameAndLastName(String firstName, String lastName);
    //<T> List<T> findAllByAddress(String address, Class<T> type);
}
