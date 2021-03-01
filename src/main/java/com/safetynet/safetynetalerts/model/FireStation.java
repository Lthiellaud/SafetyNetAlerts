package com.safetynet.safetynetalerts.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    public FireStation(Long id, String address, Integer station) {
        this.id = id;
        this.address = address;
        this.station = station;
    }

    public FireStation() {
    }
}
