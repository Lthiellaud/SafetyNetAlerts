package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.FloodDTO;

import java.util.List;

/**
 * Service used by Flood Controller
 */
public interface FloodService {
    /**
     * to get the list of the households attached to the given fire stations.
     * @param stations the list of fire stations for which we need the list
     * @return the list of households by address including name, phone, age
     * medical record of each member
     */
    List<FloodDTO> getFloodList(List<Integer> stations);
}
