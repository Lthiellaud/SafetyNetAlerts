package com.safetynet.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    /*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MedicalRecord that = (MedicalRecord) o;
        boolean result = (this.firstName == that.getFirstName())
                && (this.lastName == that.getLastName())
                && (this.birthdate == that.getBirthdate());
        return result;
    }*/
}
