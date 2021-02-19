package com.safetynet.safetynetalerts.model.DTO;

import lombok.Getter;

import java.util.List;

@Getter
public class ChildAlertDTO {

    private List<PersonAlertDTO> children;
    private List<PersonAlertDTO> adults;

    public ChildAlertDTO(List<PersonAlertDTO> children, List<PersonAlertDTO> adults) {
        this.children = children;
        this.adults = adults;
    }
}
