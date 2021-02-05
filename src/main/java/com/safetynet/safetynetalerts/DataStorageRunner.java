package com.safetynet.safetynetalerts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.FireStationService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * Reads the file data.json and stores data in the database
 */
@Component
public class DataStorageRunner implements CommandLineRunner {

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Reads the file via an InputStream and save each node in the corresponding entity.
     * @param args default argument
     * @throws Exception
     */
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/data.json");
        String data = mapper.readTree(inputStream).toString();

        // Save persons node from data.json to Person entity
        TypeReference<List<Person>> typeRefperson = new TypeReference<List<Person>>() {};
        JsonNode personNode = mapper.readTree(data).get("persons");
        List<Person> persons = mapper.readValue(personNode.toString(), typeRefperson);
        personService.savePersonList(persons);
        System.out.println("persons saved !");

        // Save firestations node from data.json to Firestation entity
        TypeReference<List<FireStation>> typeRefFireStation = new TypeReference<List<FireStation>>() {};
        JsonNode fireStationNode = mapper.readTree(data).get("firestations");
        List<FireStation> fireStations = mapper.readValue(fireStationNode.toString(), typeRefFireStation);
        fireStationService.saveFireStationList(fireStations);
        System.out.println("fire stations saved !");

        // Save medicalrecords node from data.json to MedicalRecord entity
        TypeReference<List<MedicalRecord>> typeRefMedicalRecord = new TypeReference<List<MedicalRecord>>() {};
        JsonNode medicalRecordNode = mapper.readTree(data).get("medicalrecords");
        List<MedicalRecord> medicalRecords = mapper.readValue(medicalRecordNode.toString(), typeRefMedicalRecord);
        medicalRecordService.saveMedicalRecordList(medicalRecords);
        System.out.println("medical records saved !");
    }
}
