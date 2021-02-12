package com.safetynet.safetynetalerts.model.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class FirePersonDTO {

    private List<Integer> stations;
    private List<PersonWithPhoneDTO> persons;

    public FirePersonDTO(List<Integer> stations, List<PersonWithPhoneDTO> persons) {
        this.stations = stations;
        this.persons = persons;
    }
}
