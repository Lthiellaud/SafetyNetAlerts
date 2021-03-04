package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.DTO.ICommunityEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.IPhoneAlertDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, PersonId> {

    //List<Person> findAllDistinctByAddressIsIn(Iterable<String> addresses);
    //<T> List<T> findByStation(Integer station, Class<T> type);

    List<IPhoneAlertDTO> findAllDistinctPhoneByAddressIsIn(Iterable<String> addresses);
    List<ICommunityEmailDTO> findAllDistinctEmailByCity(String city);
    List<PersonMedicalRecordDTO> findAllByAddress(String address);
    List<PersonMedicalRecordDTO> findAllByLastName(String lastName);
    //<T> List<T> findAllByAddress(String address, Class<T> type);
}
