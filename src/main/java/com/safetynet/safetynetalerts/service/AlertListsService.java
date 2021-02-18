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
    public List<PersonPhoneMedicalRecordDTO> getPersonPhoneMedicalRecordDTO(String address) {
        List<PersonMedicalRecordDTO> persons = personService.getAllByAddress(address);
        List<PersonPhoneMedicalRecordDTO> personList = new ArrayList<>();
        if (persons.size() > 0) {
            getMedicalRecord(persons);
            persons.forEach(person -> personList.add(new PersonPhoneMedicalRecordDTO(person)));
        }
        return personList;
    }

    public void getMedicalRecord(List<PersonMedicalRecordDTO> persons) {
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
            List<PersonByAddressDTO> personByAddressList = new ArrayList<>();
            List<String> addresses = fireStationService.getAddresses(station);
            if (addresses.size() > 0) {
                addresses.forEach(address -> {
                    PersonByAddressDTO p = new PersonByAddressDTO();
                    p.setAddress(address);
                    p.setPersons(getPersonPhoneMedicalRecordDTO(address));
                    personByAddressList.add(p);
                });
            }
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
        List<PersonMedicalRecordDTO> persons =
                personService.getAllByFirstAndLastName(firstName, lastName);
        getMedicalRecord(persons);
        List<PersonEmailMedicalRecordDTO> personList = new ArrayList<>();
        persons.forEach(person -> personList.add(new PersonEmailMedicalRecordDTO(person)));
        return personList;
    }

}
