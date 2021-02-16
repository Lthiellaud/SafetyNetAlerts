package com.safetynet.safetynetalerts.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PersonByAddressDTO {
    private String address;
    private List<PersonPhoneMedicalRecordDTO> persons;
}
