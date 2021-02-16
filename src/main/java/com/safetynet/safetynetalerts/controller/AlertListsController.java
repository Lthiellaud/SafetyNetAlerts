package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.DTO.FirePersonDTO;
import com.safetynet.safetynetalerts.model.DTO.FloodListByStationDTO;
import com.safetynet.safetynetalerts.model.DTO.IPersonEmailDTO;
import com.safetynet.safetynetalerts.model.DTO.IPersonPhoneDTO;
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
     * Response to url http://localhost:9090/communityEmail?city=<city>.
     * @param city the city for which we need all the inhabitants email
     * @return the email list of the city inhabitants
     */
    @GetMapping("/communityEmail")
    public List<IPersonEmailDTO> getEmails(@RequestParam("city") String city) {
        if (city.equals("")) {
            logger.error("URL /communityEmail Request : a parameter \"city\" is needed");
            return null;
        }
        List<IPersonEmailDTO> emailList = alertListsService.getEmailList(city);
        if (emailList.size() > 0){
            logger.info("URL /communityEmail Request : Email of all inhabitants of " + city + " sent");

        } else {
            logger.error("URL /communityEmail Request : no inhabitants found for city " +
                     city);

        }
        return emailList;
    }

    /**
     * Response to URL http://localhost:9090/fire?address=<address>.
     * @param address the address for which we need information
     * @return fire station number attached to the given address and list of the persons
     * living at this address including phone, age, medical record
     */
    @GetMapping("/fire")
    public FirePersonDTO getFirePersons(@RequestParam("address") String address) {
        if (address.equals("")) {
            logger.error("URL /fire Request : a parameter \"address\" is needed");
            return null;
        }
        FirePersonDTO firePersonDTO = alertListsService.getFirePersonList(address);
        /*if (firePersonDTO.getStations().size() > 0) {
            logger.info("URL /fire : fire list for address " + address + " sent");
        } else {
            logger.info("URL /fire : empty fire list for address " + address + " sent");
        }*/
        return firePersonDTO;
    }

    /**
     * URL http://localhost:9090/phoneAlert?firestation=<firestation_number>.
     * @param fireStation the number of the fire Station we need
     * @return the phone number list of the persons attached to fire station number "fireStation"
     */
    @GetMapping("/phoneAlert")
    public Iterable<IPersonPhoneDTO> getPhoneList(@RequestParam("firestation") Integer fireStation) {
        List<IPersonPhoneDTO> phones = alertListsService.getPhones(fireStation);
        if (phones.size() != 0) {
            logger.info("URL /phoneAlert : List of inhabitants phone for fire station " +
                    + fireStation + " sent");
            return phones;
        } else {
            logger.error("URL /phoneAlert : no inhabitants found for fire station " +
                    fireStation);
            return null;
        }
    }

    /**
     * URL 1-	http://localhost:9090/flood/stations?stations=2 3
     * @param stations the list of station for which we need the list
     * @return a list of the households attached to the given stations
     */
    @GetMapping("/flood/stations")
    public List<FloodListByStationDTO> getFloodList (@RequestParam ("stations") List<Integer> stations) {
        return alertListsService.getFloodList(stations);
    }


}
