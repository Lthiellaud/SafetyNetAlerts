package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * gives access to MedicalRecord records.
 */
@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, PersonId> {

}
