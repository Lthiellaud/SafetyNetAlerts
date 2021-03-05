package com.safetynet.safetynetalerts.model.DTO;

import lombok.Getter;

import java.util.List;

/**
 * Class for URL /childAlert?address=<address>
 */
@Getter
public class ChildAlertDTO {

    private String homehood;
    private List<PersonAlertDTO> children;
    private List<PersonAlertDTO> adults;

    public ChildAlertDTO(String homehood, List<PersonAlertDTO> children,
                         List<PersonAlertDTO> adults) {
        this.homehood = homehood;
        this.children = children;
        this.adults = adults;
    }
}
