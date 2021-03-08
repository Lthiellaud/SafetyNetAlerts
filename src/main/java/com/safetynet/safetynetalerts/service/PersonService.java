package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.ICommunityEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.IPhoneAlertDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonId;

import java.util.List;
import java.util.Optional;

/**
 * Service used by Person Controller
 */
public interface PersonService {
    /**
     * To apply the modifications to a person. Only non null attributes will be changed.
     * @param person The modified person record to be used for updating the person
     * @return the founded person or an empty optional if nothing done
     */
    Optional<Person> updatePerson(Person person);

    /**
     * To verify if the person defined by firstname and lastname exists.
     * @param personId defines firstname and lastname of the person
     * @return true if the person has been found, false otherwise
     */
    boolean getPersonId(PersonId personId);

    /**
     * To add a person.
     * @param person the person to be added
     * @return the saved person or an empty optional if nothing done
     */
    Optional<Person> createPerson(Person person);

    /**
     * To add a list of persons.
     * @param persons the list of persons to be added
     * @return the saved person
     */
    Iterable<Person> savePersonList(List<Person> persons);

    /**
     * To delete a person from his firstname and lastname.
     * @param firstName defines firstname of the person
     * @param lastName defines lastname of the person
     * @return true if a record has been deleted, false otherwise
     */
    boolean deletePerson(String firstName, String lastName);

    /**
     * To get the email list of all the inhabitants of a city.
     * @param city The city for which an email list is needed
     * @return the email list
     */
    List<ICommunityEmailDTO> getEmailList(String city);

    /**
     * To get all the phone numbers of corresponding to a list of address.
     * @param addresses the list of addresses for which we need the phone number
     * @return the list of phone number
     */
    List<IPhoneAlertDTO> getPhones(List<String> addresses);

    /**
     * to get the list of the inhabitants at the given address.
     * @param address The address for which we need the inhabitants list
     * @return the inhabitants list in PersonMedicalRecordDTO projection
     */
    List<PersonMedicalRecordDTO> getAllByAddress(String address);

    /**
     * To get the list of persons with the given firstName and lastName.
     * If firstName value is omitted, all persons with the given lastName will be sent
     * @param firstName firstName value is optional
     * @param lastName lastName value is mandatory
     * @return the list of persons including address, age, email and medical information
     */
    List<PersonMedicalRecordDTO>
                getAllByFirstAndLastName(String firstName, String lastName);
}
