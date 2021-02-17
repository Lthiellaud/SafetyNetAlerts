package com.safetynet.safetynetalerts.model.DTO;

import com.safetynet.safetynetalerts.model.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonEmailMedicalRecordDTO {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private List<String> medications;
    private List<String> allergies;

    public PersonEmailMedicalRecordDTO(String firstName, String lastName, String address, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    public PersonEmailMedicalRecordDTO(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.email = person.getEmail();
    }

}
