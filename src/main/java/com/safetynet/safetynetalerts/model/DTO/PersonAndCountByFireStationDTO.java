package com.safetynet.safetynetalerts.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class used for URL /firestation{@literal ?}stationNumber={station_number}.
 */
@Setter
@Getter
public class PersonAndCountByFireStationDTO {
    private List<PersonByFireStationDTO> personsByFireStation;
    private Long childrenCount;
    private Long adultsCount;

}
