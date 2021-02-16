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
    private static DateUtil dateUtil;

    /**
     * To get the email list of all the inhabitants of a city.
     * @param city The city for which an email list is needed
     * @return the email list
     */
    public List<IPersonEmailDTO> getEmailList(String city){
        return personService.getEmailList(city);
    }

    public FirePersonDTO getFirePersonList(String address) {
        return new FirePersonDTO(fireStationService.getStations(address),
                getPersonPhoneMedicalRecordDTO(address));
    }

    private List<PersonPhoneMedicalRecordDTO> getPersonPhoneMedicalRecordDTO(String address) {
        List<PersonPhoneMedicalRecordDTO> persons = personService.getAllByAddress(address);
        dateUtil = new DateUtil();
        persons.forEach(person -> {
            personId = new PersonId(person.getFirstName(), person.getLastName());
            Optional<MedicalRecord> m = medicalRecordService.getPersonMedicalRecord(personId);
            if (m.isPresent()) {
                MedicalRecord medicalRecord = m.get();
                person.setAge(dateUtil.age(medicalRecord.getBirthdate()));
                person.setMedications(medicalRecord.getMedications());
                person.setAllergies(medicalRecord.getAllergies());
            }
        });
        return persons;
    }

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

    public List<IPersonPhoneDTO> getPhones(Integer station) {
        List<String> addresses = fireStationService.getAddresses(station);
        return personService.getPhones(addresses);
    }

}
