package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="medicalrecords")
@IdClass(PersonId.class)
public class MedicalRecord {

    @Id
    private String firstName;

    @Id
    private String lastName;

    @JsonFormat(pattern="MM/dd/yyyy")
    private Date birthdate;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;
}
