package com.safetynet.safetynetalerts.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class LoadFileService {

    private static Logger logger = LoggerFactory.getLogger(LoadFileService.class);

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private Environment env;

    /**
     *  Reads the file data.json via an InputStream and save each node in the corresponding entity.
     */
    public void execute() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String path = env.getProperty("dataPath");
            System.out.println(path);
            if (path == null) {
                throw new Exception("Missing Required Property : [dataPath]");
            } else {
                try (InputStream inputStream = TypeReference.class.getResourceAsStream(path)) {
                    String data = mapper.readTree(inputStream).toString();

                    // Save persons node from data.json to Person entity
                    TypeReference<List<Person>> typeRefperson = new TypeReference<List<Person>>() {
                    };
                    JsonNode personNode = mapper.readTree(data).get("persons");
                    List<Person> persons = mapper.readValue(personNode.toString(), typeRefperson);
                    personService.savePersonList(persons);
                    logger.info("data persons saved from file "+ path + " !");

                    // Save firestations node from data.json to Firestation entity
                    TypeReference<List<FireStation>> typeRefFireStation = new TypeReference<List<FireStation>>() {
                    };
                    JsonNode fireStationNode = mapper.readTree(data).get("firestations");
                    List<FireStation> fireStations = mapper.readValue(fireStationNode.toString(), typeRefFireStation);
                    fireStationService.saveFireStationList(fireStations);
                    logger.info("data fire stations from file "+ path + " !");

                    // Save medicalrecords node from data.json to MedicalRecord entity
                    TypeReference<List<MedicalRecord>> typeRefMedicalRecord = new TypeReference<List<MedicalRecord>>() {
                    };
                    JsonNode medicalRecordNode = mapper.readTree(data).get("medicalrecords");
                    List<MedicalRecord> medicalRecords = mapper.readValue(medicalRecordNode.toString(), typeRefMedicalRecord);
                    medicalRecordService.saveMedicalRecordList(medicalRecords);
                    logger.info("data medical records from file "+ path + " !");
                } catch (IOException e) {
                    logger.error("error while reading " + path, e);
                }
            }
        } catch (Exception e) {
            logger.error("Missing Required Property : [dataPath]", e);
        }
    }

}
