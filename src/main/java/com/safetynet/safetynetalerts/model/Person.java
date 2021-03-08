package com.safetynet.safetynetalerts.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Contains the data person: (firstname, lastname)=unique id, full address, phone, email.
 */
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
