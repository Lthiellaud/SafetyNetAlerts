package com.safetynet.safetynetalerts.model.DTO;

/**
 * Interface-based projection of the class Person.
 * Used for URL /phoneAlert{@literal ?}firestation={firestation_number}
 */
public interface IPhoneAlertDTO {
    String getPhone();

}
