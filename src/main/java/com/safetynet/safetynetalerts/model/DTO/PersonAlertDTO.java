package com.safetynet.safetynetalerts.model.DTO;

import lombok.Getter;

/**
 * Class used for URL /childAlert
 */
@Getter
public class PersonAlertDTO {

    private String firstName;
    private String lastName;
    private Integer age;

    public PersonAlertDTO(PersonMedicalRecordDTO person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = person.getAge();
    }
}
