package com.safetynet.safetynetalerts.model;

import lombok.Data;

import java.util.List;

@Data
public class PersonDto {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private int age;
    private List<String> medications;
    private List<String> allergies;
    private List<Integer> stations;

    public PersonDto(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }



}
