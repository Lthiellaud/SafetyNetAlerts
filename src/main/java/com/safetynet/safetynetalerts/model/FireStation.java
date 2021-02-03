package com.safetynet.safetynetalerts.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "firestations")
public class FireStation {

    @Id
    private String address;

    private Integer station;
}
