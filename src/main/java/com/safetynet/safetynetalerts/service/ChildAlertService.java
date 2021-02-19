package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.ChildAlertDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonAlertDTO;
import com.safetynet.safetynetalerts.model.DTO.PersonMedicalRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildAlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChildAlertService.class);

    @Autowired
    private AlertListsService alertListsService;

    public List<ChildAlertDTO> getChildAlertList(String address) {
        List<ChildAlertDTO> childAlertList = new ArrayList<>();

        //get the list of person/medical record at th given address
        List<PersonMedicalRecordDTO> personMedicalRecordList =
                alertListsService.getMedicalRecordByAddress(address);

        //filter to get the children
        List<PersonMedicalRecordDTO> children = personMedicalRecordList.stream()
                .filter(p -> p.getAge() <= 18).collect(Collectors.toList());

        int i = children.size();
        if (i > 0) {
            List<PersonAlertDTO> childList = new ArrayList<>();
            children.forEach(c -> childList.add(new PersonAlertDTO(c)));
            List<PersonMedicalRecordDTO> adults = personMedicalRecordList.stream()
                    .filter(p -> p.getAge() > 18).collect(Collectors.toList());
            List<PersonAlertDTO> adultList = new ArrayList<>();
            adults.forEach(a -> adultList.add(new PersonAlertDTO(a)));
            childAlertList.add(new ChildAlertDTO(childList, adultList));
            LOGGER.info("getChilAlertList: childAlertList constructed with " + i +
                    " child(ren) and " + adultList.size() + " adult(s)");
        }
        return childAlertList;
    }
}
