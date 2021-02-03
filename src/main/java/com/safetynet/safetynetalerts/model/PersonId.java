package com.safetynet.safetynetalerts.model;

import java.io.Serializable;
import java.util.Objects;

public class PersonId implements Serializable {

    private String firstName;
    private String lastName;

    public PersonId() {}

    public PersonId (String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonId personId = (PersonId) o;
        return firstName.equals(personId.firstName) &&
                lastName.equals(personId.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
