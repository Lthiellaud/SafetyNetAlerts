package com.safetynet.safetynetalerts.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StationDependentPerson {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
