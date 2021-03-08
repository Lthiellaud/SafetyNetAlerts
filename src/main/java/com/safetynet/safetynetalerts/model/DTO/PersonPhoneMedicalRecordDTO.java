package com.safetynet.safetynetalerts.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class used in FireDTO class and PersonByAddressDTO class.
 */
@Getter
@Setter
public class PersonPhoneMedicalRecordDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public PersonPhoneMedicalRecordDTO(PersonMedicalRecordDTO person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.age = person.getAge();
        this.medications = person.getMedications();
        this.allergies = person.getAllergies();
    }

    public PersonPhoneMedicalRecordDTO() { }
}
