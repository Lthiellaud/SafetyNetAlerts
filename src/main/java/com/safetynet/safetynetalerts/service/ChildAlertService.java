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
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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

        String message = "";

        //group by homehood
        Map<String, List<PersonMedicalRecordDTO>> homeHoodList1 = personMedicalRecordList.stream()
                .collect(groupingBy(PersonMedicalRecordDTO::getLastName));

        Map<String, List<PersonMedicalRecordDTO>> homeHoodList = new TreeMap<>(homeHoodList1);

        //for each homehood, filter to get the children
        for (Map.Entry<String, List<PersonMedicalRecordDTO>> entry : homeHoodList.entrySet()) {
            String name = entry.getKey();
            List<PersonMedicalRecordDTO> list = entry.getValue();
            List<PersonMedicalRecordDTO> children = list.stream()
                    .filter(p -> p.getAge() <= 18).collect(Collectors.toList());

            int i = children.size();
            if (i > 0) {
                List<PersonAlertDTO> childList = new ArrayList<>();
                children.forEach(c -> childList.add(new PersonAlertDTO(c)));
                List<PersonMedicalRecordDTO> adults = list.stream()
                        .filter(p -> p.getAge() > 18).collect(Collectors.toList());
                List<PersonAlertDTO> adultList = new ArrayList<>();
                adults.forEach(a -> adultList.add(new PersonAlertDTO(a)));
                childAlertList.add(new ChildAlertDTO(name, childList, adultList));
                message += "homehood " + name + ": " + i +
                        " child(ren) and " + adultList.size() + " adult(s)\n";
            } else {
                message += "homehood " + name + ": no child";
            }
        }
        LOGGER.info("getChildAlertList: " + address + " \n" + message);
        return childAlertList;
    }
}
