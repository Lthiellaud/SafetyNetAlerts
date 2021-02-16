package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.IPersonEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.IPersonPhoneDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonEmailMedicalRecordDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
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
     * To apply the modifications to a person. Only non null attributes will be changed.
     * @param person The modified person record to be used for updating the person
     * @return the founded person
     */
    public Optional<Person> updatePerson(Person person){
        PersonId personId = new PersonId(person.getFirstName(), person.getLastName());
        Optional<Person> p = personRepository.findById(personId);
        if (p.isPresent()) {
            Person currentPerson = p.get();
            String address = person.getAddress();
            if (address != null) {
                currentPerson.setAddress(address);
            }
            String city = person.getCity();
            if (city != null) {
                currentPerson.setCity(city);
            }
            Integer zip = person.getZip();
            if (zip != null) {
                currentPerson.setZip(zip);
            }
            String phone = person.getPhone();
            if (phone != null) {
                currentPerson.setPhone(phone);
            }
            String email = person.getEmail();
            if (email != null) {
                currentPerson.setEmail(email);
            }
            return Optional.of(currentPerson);
        } else {
            return p;
        }


    }

    /**
     * To verify if the person defined by firstname and lastname exists.
     * @param personId defines firstname and lastname of the person
     * @return the founded person
     */
    public boolean getPersonId(PersonId personId){
        return personRepository.findById(personId).isPresent();
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

    /**
     * To get the email list of all the inhabitants of a city.
     * @param city The city for which an email list is needed
     * @return the email list
     */
    public List<IPersonEmailDTO> getEmailList(String city){
        return personRepository.findAllDistinctEmailByCity(city);
    }

    /**
     * To get all the phone numbers of corresponding to a list of address
     * @param addresses the list of addresses for which we need the phone number
     * @return the list of phone number
     */
    public List<IPersonPhoneDTO> getPhones(List<String> addresses) {
        return personRepository.findAllDistinctPhoneByAddressIsIn(addresses);
    }

    /**
     * to get the list of the inhabitants at the given address
     * @param address The address for which we need the inhabitants list
     * @return the inhabitants list in PersonWitPhoneDTO projection
     */
    public List<PersonPhoneMedicalRecordDTO> getAllByAddress(String address) {
        return personRepository.findAllByAddress(address);
    }

    public List<PersonEmailMedicalRecordDTO> getAllByFirstAndLastName(String firstName, String lastName) {
        return personRepository.findAllByFirstNameAndLastName(firstName, lastName);
    }
}
