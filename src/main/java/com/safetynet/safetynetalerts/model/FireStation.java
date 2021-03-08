package com.safetynet.safetynetalerts.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Contains the records address/matching fire station number.
 */
@Entity
@Data
@Table(name = "firestations")
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private Integer station;

    public FireStation(Long id, String address, Integer station) {
        this.id = id;
        this.address = address;
        this.station = station;
    }

    public FireStation() {
    }
}
