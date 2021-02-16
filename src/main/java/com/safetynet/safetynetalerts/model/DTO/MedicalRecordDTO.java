package com.safetynet.safetynetalerts.model.DTO;

import com.safetynet.safetynetalerts.model.PersonId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MedicalRecordDTO {

    /*private String firstName;
    private String lastName;*/
    private PersonId personId;
    private Date birthdate;
    private int age;
    private List<String> medications;
    private List<String> allergies;

    public MedicalRecordDTO(PersonId personId, //String firstName, String lastName,
                            Date birthdate, List<String> medications, List<String> allergies) {
//        this.firstName = firstName;
//        this.lastName = lastName;
        this.personId = personId;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;

    }
}
