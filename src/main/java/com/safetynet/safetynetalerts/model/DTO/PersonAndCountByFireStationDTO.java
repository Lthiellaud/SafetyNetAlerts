package com.safetynet.safetynetalerts.model.DTO;

import lombok.Setter;

import java.util.List;

@Setter
public class PersonAndCountByFireStationDTO {
    private List<PersonByFireStationDTO> personsByFireStation;
    private Long childrenCount;
    private Long adultsCount;

}
