package com.safetynet.safetynetalerts.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class for URL /flood/stations?stations=<a list of station_numbers>
 */
@Getter
@AllArgsConstructor
public class FloodDTO {

    private Integer station;
    private List<PersonByAddressDTO> addresses;
}
