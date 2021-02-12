package com.safetynet.safetynetalerts.model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PersonWithPhoneDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public PersonWithPhoneDTO(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
}
