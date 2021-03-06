package com.safetynet.safetynetalerts.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * Defines a person by his firstname and lastname.
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class PersonId implements Serializable {

    private String firstName;
    private String lastName;

    public PersonId() { }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
