package com.safetynet.safetynetalerts.model.DTO;

import java.util.List;

public class PersonMedicalRecordDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public PersonMedicalRecordDTO(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

}
