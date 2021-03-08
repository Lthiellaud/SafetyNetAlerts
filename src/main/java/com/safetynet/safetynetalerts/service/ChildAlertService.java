package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.ChildAlertDTO;

import java.util.List;

/**
 * Service used by ChildAlert Controller
 */
public interface ChildAlertService {
    /**
     * to give a list of the children living at the given address.
     * @param address Address for which the child list is required
     * @return the child list group by homehood with first name, last name, age and a list of other
     *         homehood members
     */
    List<ChildAlertDTO> getChildAlertList(String address);
}
