package com.safetynet.safetynetalerts.model;

public interface IPersonPhone {
    String getPhone();

    @Override
    boolean equals(Object o);
    @Override
    int hashCode();
}
