package com.safetynet.safetynetalerts.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "persons")
@IdClass(PersonId.class)
public class Person {

    @Id
    private String firstName;
    @Id
    private String lastName;

    private String address;
    private String city;
    private Integer zip;
    private String phone;
    private String email;

}
