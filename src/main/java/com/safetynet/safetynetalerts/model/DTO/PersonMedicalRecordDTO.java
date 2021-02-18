package com.safetynet.safetynetalerts.model.DTO;

import com.safetynet.safetynetalerts.model.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonMedicalRecordDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public PersonMedicalRecordDTO(String firstName, String lastName, String address, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public PersonMedicalRecordDTO(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.email = person.getEmail();
    }

}
