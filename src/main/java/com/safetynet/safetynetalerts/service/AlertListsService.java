package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.*;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlertListsService {

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    PersonService personService;

    @Autowired
    FireStationService fireStationService;

    private static PersonId personId;
    private static DateUtil dateUtil = new DateUtil();

    /**
     * To get the email list of all the inhabitants of a city.
     * @param city The city for which an email list is needed
     * @return the email list
     */
    public List<IPersonEmailDTO> getEmailList(String city){
        return personService.getEmailList(city);
    }

    /**
     * To get the list of the inhabitants living at an address and the attached fire station.
     * @param address the address for which we need the inhabitants list
     * @return the list of inhabitants at the given address including phone and medical record
     * and the fire stations attached to this address
     */
    public FirePersonDTO getFirePersonList(String address) {
        return new FirePersonDTO(fireStationService.getStations(address),
                getPersonPhoneMedicalRecordDTO(address));
    }

    /**
     * To get the list of the inhabitants living at an address.
     * @param address the address for which we need the inhabitants list
     * @return the list of inhabitants at the given address including phone and medical record
     */
    private List<PersonPhoneMedicalRecordDTO> getPersonPhoneMedicalRecordDTO(String address) {
        List<PersonPhoneMedicalRecordDTO> persons = personService.getAllByAddress(address);
        persons.forEach(person -> {
            personId = new PersonId(person.getFirstName(), person.getLastName());
            Optional<MedicalRecord> m = medicalRecordService.getMedicalRecord(personId);
            if (m.isPresent()) {
                MedicalRecord medicalRecord = m.get();
                person.setAge(dateUtil.age(medicalRecord.getBirthdate()));
                person.setMedications(medicalRecord.getMedications());
                person.setAllergies(medicalRecord.getAllergies());
            }
        });
        return persons;
    }

    /**
     * to get the list of the households attached to the given fire stations.
     * @param stations the list of fire stations for which we need the list
     * @return the list of households by address including name, phone, age
     * medical record of each member
     */
    public List<FloodListByStationDTO> getFloodList(List<Integer> stations) {
        List<FloodListByStationDTO> floodList = new ArrayList<>();
        for (Integer station : stations) {
            List<String> addresses = fireStationService.getAddresses(station);
            List<PersonByAddressDTO> personByAddressList = new ArrayList<>();
            addresses.forEach(address -> {
                PersonByAddressDTO p = new PersonByAddressDTO();
                p.setAddress(address);
                p.setPersons(getPersonPhoneMedicalRecordDTO(address));
                personByAddressList.add(p);
            });
            floodList.add(new FloodListByStationDTO(station, personByAddressList));
        }
        return floodList;
    }

    /**
     * To get the phone list of the inhabitants attached to a given fire station.
     * @param station the station number of the fire station
     * @return the phone list of the inhabitants attached to a given fire station
     */
    public List<IPersonPhoneDTO> getPhones(Integer station) {
        List<String> addresses = fireStationService.getAddresses(station);
        return personService.getPhones(addresses);
    }

    /**
     * To get the inhabitants by firstname and lastname.
     * @param firstName the firstname sought
     * @param lastName the last name sought
     * @return the list including address, age, email, medical record
     */
    public List<PersonEmailMedicalRecordDTO> getPersonEmailMedicalRecord(String firstName, String lastName) {
        List<PersonEmailMedicalRecordDTO> persons =
                personService.getAllByFirstAndLastName(firstName, lastName);
        persons.forEach(person -> {
            personId = new PersonId(person.getFirstName(), person.getLastName());
            Optional<MedicalRecord> m = medicalRecordService.getMedicalRecord(personId);
            if (m.isPresent()) {
                MedicalRecord medicalRecord = m.get();
                person.setAge(dateUtil.age(medicalRecord.getBirthdate()));
                person.setMedications(medicalRecord.getMedications());
                person.setAllergies(medicalRecord.getAllergies());
            }
        });
        return persons;
    }

}
