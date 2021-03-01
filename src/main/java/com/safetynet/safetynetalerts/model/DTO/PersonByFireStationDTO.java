package com.safetynet.safetynetalerts.model.DTO;

public class PersonByFireStationDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public PersonByFireStationDTO (PersonMedicalRecordDTO person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
    }
}
