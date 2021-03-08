package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import com.safetynet.safetynetalerts.service.PersonInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class PersonInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonInfoController.class);
    @Autowired
    private PersonInfoService personInfoService;

   /**
     * URL /personInfo?firstName=<firstName>&lastName=<lastName>.
     * @param firstName the firstname sought
     * @param lastName the lastname sought
     * @return the list of inhabitants including address, age, email, medical record
     */
    @GetMapping("/personInfo")
    public ResponseEntity<MappingJacksonValue>
                getPersonInfoList(@RequestParam("firstName") String firstName,
                                  @RequestParam("lastName") String lastName) {
        if (lastName.equals("")) {
            LOGGER.error("URL /personInfo request: value for lastName is mandatory");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("URL /personInfo: request received for " + firstName + " " + lastName);
        List<PersonMedicalRecordDTO> persons =
                personInfoService.getPersonInfo(firstName, lastName);

        if (persons.size() > 0) {
            //put a filter on PersonMedicalRecordDTO to remove phone
            SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter
                    .serializeAllExcept("phone");
            FilterProvider filterList = new SimpleFilterProvider()
                    .addFilter("myDynamicFilter", myFilter);
            MappingJacksonValue personsFilters = new MappingJacksonValue(persons);
            personsFilters.setFilters(filterList);
            LOGGER.info("URL /personInfo: List sent for " + firstName + " " + lastName);
            return new ResponseEntity<>(personsFilters, HttpStatus.OK);
        } else {
            LOGGER.info("URL /personInfo: nobody found with name " + firstName + " " + lastName);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
