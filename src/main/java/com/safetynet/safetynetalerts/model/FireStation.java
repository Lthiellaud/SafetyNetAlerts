package com.safetynet.safetynetalerts.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "firestations")
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private Integer station;
}
