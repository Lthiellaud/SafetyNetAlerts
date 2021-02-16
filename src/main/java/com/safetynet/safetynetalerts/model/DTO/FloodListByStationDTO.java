package com.safetynet.safetynetalerts.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class FloodListByStationDTO {

    private Integer station;
    private List<PersonByAddressDTO> addresses;
}
