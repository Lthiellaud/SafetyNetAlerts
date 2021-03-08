package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.DTO.IPhoneAlertDTO;
import com.safetynet.safetynetalerts.service.implementation.FireStationServiceImpl;
import com.safetynet.safetynetalerts.service.implementation.PersonServiceImpl;
import com.safetynet.safetynetalerts.service.implementation.PhoneAlertServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "command.line.runner.enabled=false")
public class PhoneAlertServiceTest {

    @MockBean
    private FireStationServiceImpl fireStationService;

    @MockBean
    private PersonServiceImpl personService;

    @Autowired
    PhoneAlertServiceImpl phoneAlertService;

    @Test
    public void getPhonesTest() {
        //GIVEN
        List<String> addresses = Arrays.asList("address11", "address12");
        IPhoneAlertDTO phone1 = () -> "phone1";
        IPhoneAlertDTO phone2 = () -> "phone2";
        List<IPhoneAlertDTO> phones = Arrays.asList(phone1, phone2);
        when(fireStationService.getAddresses(1)).thenReturn(addresses);
        when(personService.getPhones(addresses)).thenReturn(phones);

        //WHEN
        List<IPhoneAlertDTO> result = phoneAlertService.getPhones(1);

        //THEN
        verify(fireStationService, times(1)).getAddresses(1);
        verify(personService, times(1)).getPhones(addresses);
        assertThat(result).isEqualTo(phones);
    }

    @Test
    public void getPhones_noAddressesFoundTest() {
        //GIVEN
        when(fireStationService.getAddresses(1)).thenReturn(new ArrayList<>());

        //WHEN
        List<IPhoneAlertDTO> result = phoneAlertService.getPhones(1);

        //THEN
        verify(fireStationService, times(1)).getAddresses(1);
        verify(personService, times(0)).getPhones(anyList());
        assertThat(result).isEmpty();
    }

    @Test
    public void getPhones_nobodyFoundTest() {
        //GIVEN
        List<String> addresses = Arrays.asList("address11", "address12");
        when(fireStationService.getAddresses(1)).thenReturn(addresses);
        when(personService.getPhones(addresses)).thenReturn(new ArrayList<>());

        //WHEN
        List<IPhoneAlertDTO> result = phoneAlertService.getPhones(1);

        //THEN
        verify(fireStationService, times(1)).getAddresses(1);
        verify(personService, times(1)).getPhones(addresses);
        assertThat(result).isEmpty();
    }
}
