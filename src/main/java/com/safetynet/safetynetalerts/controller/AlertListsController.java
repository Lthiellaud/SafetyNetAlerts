package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.*;
import com.safetynet.safetynetalerts.service.AlertListsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AlertListsController {

    private Logger logger = LoggerFactory.getLogger(AlertListsController.class);
    @Autowired
    AlertListsService alertListsService;



    /**
     * URL /phoneAlert?firestation=<firestation_number>.
     * @param fireStation the number of the fire Station we need
     * @return the phone number list of the persons attached to fire station number "fireStation"
     */
    @GetMapping("/phoneAlert")
    public Iterable<IPersonPhoneDTO> getPhoneList(@RequestParam("firestation") Integer fireStation) {
        List<IPersonPhoneDTO> phones = alertListsService.getPhones(fireStation);
        if (phones.size() != 0) {
            logger.info("URL /phoneAlert request: List of inhabitants phone for fire station " +
                    + fireStation + " sent");
            return phones;
        } else {
            logger.error("URL /phoneAlert request: no inhabitants found for fire station " +
                    fireStation);
            return null;
        }
    }

    /**
     * URL /flood/stations?stations=<a list of station_numbers>.
     * @param stations the list of station for which we need the list (integers separated by comma)
     * @return a list of the households attached to the given stations
     */
 /*   @GetMapping("/flood/stations")
    public List<FloodListByStationDTO> getFloodList
            (@RequestParam ("stations") List<Integer> stations) {
        if (stations != null && stations.size() > 0) {
            logger.info("URL /flood/stations request: List sent for stations " + stations);
            return alertListsService.getFloodList(stations);
        } else {
            logger.error("URL /flood/stations request: station list is needed");
            return null;
        }
    }
*/
    /**
     * URL /personInfo?firstName=<firstName>&lastName=<lastName>.
     * @param firstName the firstname sought
     * @param lastName the lastname sought
     * @return the list of inhabitants including address, age, email, medical record
     */
    @GetMapping("/personInfo")
    public List<PersonEmailMedicalRecordDTO> getPersonInfoList
            (@RequestParam("firstName") String firstName,
             @RequestParam("lastName") String lastName) {
        if (lastName.equals("")) {
            logger.error("URL /personInfo request: value for lastName is mandatory");
            return null;
        }
        List<PersonEmailMedicalRecordDTO> persons =
                alertListsService.getPersonEmailMedicalRecord(firstName, lastName);
        if (persons != null && persons.size() > 0) {
            logger.info("URL /personInfo request: List for " + firstName + " " + lastName + " sent");
        } else {
            logger.error("URL /personInfo request: nobody found with name " + firstName + " " + lastName);
        }
        return persons;
    }
}
