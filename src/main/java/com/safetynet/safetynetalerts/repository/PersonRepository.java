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

    /**
     * To retrieve a list of phone number from a list of addresses.
     * @param addresses the list of addresses for which we need the list
     * @return The list of IPhoneAlertDTO interface projection found
     */
    List<IPhoneAlertDTO> findAllDistinctPhoneByAddressIsIn(Iterable<String> addresses);

    /**
     * To retrieve the list of email of the inhabitants of a given city.
     * @param city the city for which we need the list
     * @return The list of ICommunityEmailDTO interface projection found
     */
    List<ICommunityEmailDTO> findAllDistinctEmailByCity(String city);

    /**
     * To get the "Person" information of the inhabitants at a given address.
     * @param address the address for which we need the list
     * @return The list of PersonMedicalRecordDTO fill with Person information
     *          (age, medications and allergies have null value)
     */
    List<PersonMedicalRecordDTO> findAllByAddress(String address);

    /**
     * To get the "Person" information of the inhabitants with the given last name.
     * @param lastName the last name for which we need the list
     * @return The list of PersonMedicalRecordDTO fill with Person information
     *           (age, medications and allergies have null value)
     */
    List<PersonMedicalRecordDTO> findAllByLastName(String lastName);

}
