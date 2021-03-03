package com.safetynet.safetynetalerts.model.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class FireDTO {

    private List<Integer> stations;
    private List<PersonPhoneMedicalRecordDTO> persons;

    public FireDTO(List<Integer> stations, List<PersonPhoneMedicalRecordDTO> persons) {
        this.stations = stations;
        this.persons = persons;
    }
}
