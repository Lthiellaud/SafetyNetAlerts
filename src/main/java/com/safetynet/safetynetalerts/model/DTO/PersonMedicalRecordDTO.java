package com.safetynet.safetynetalerts.model.DTO;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.safetynet.safetynetalerts.model.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class-based projection of the class Person.
 * Used each time mixed person and medical record data are needed
 */
@Getter
@Setter
@JsonFilter("myDynamicFilter")
public class PersonMedicalRecordDTO {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String phone;
    private String email;
    private List<String> medications;
    private List<String> allergies;

    //enable to use the class as a projection of Person class
    //Implicitly use in PersonRepository
    public PersonMedicalRecordDTO(String firstName, String lastName,
                                  String address, String phone, String email) {
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
        this.phone = person.getPhone();
        this.email = person.getEmail();
    }

    public PersonMedicalRecordDTO() { }
}
