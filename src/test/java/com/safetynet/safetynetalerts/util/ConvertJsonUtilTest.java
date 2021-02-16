package com.safetynet.safetynetalerts.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.safetynet.safetynetalerts.model.DTO.FirePersonDTO;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.DTO.PersonPhoneMedicalRecordDTO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ConvertJsonUtilTest {

    private Person person1 = new Person();
    private ConvertJsonUtil convertJsonUtil = new ConvertJsonUtil();

    DefaultPrettyPrinter pp = new DefaultPrettyPrinter()
            .withoutSpacesInObjectEntries()
            .withArrayIndenter(new DefaultPrettyPrinter.NopIndenter());
    private ObjectWriter writer = new ObjectMapper()
            .writer()
            .with(pp);


    @Test
    void ConvertClassToJsonTest() throws JsonProcessingException {
        person1.setFirstName("Baby");
        person1.setLastName("Family12");
        person1.setAddress("address12");
        person1.setCity("Culver");
        person1.setZip(97451);
        person1.setPhone("phone1");
        person1.setEmail("mail.test1@email.com");

        PersonPhoneMedicalRecordDTO personPhoneMedicalRecordDTO = new PersonPhoneMedicalRecordDTO("P1", "N1", "Ph1");
        FirePersonDTO firePersonDTO = new FirePersonDTO(Arrays.asList(1,2), Arrays.asList(personPhoneMedicalRecordDTO));

        System.out.println(writer.writeValueAsString(firePersonDTO));
        //String result = convertJsonUtil.ConvertClassToJson(firePersonDTO);

        /*assertThat(result).isEqualToIgnoringWhitespace("{\n" +
                "  \"firstName\" : \"Baby\",\n" +
                "  \"lastName\" : \"Family12\",\n" +
                "  \"address\" : \"address12\",\n" +
                "  \"city\" : \"Culver\",\n" +
                "  \"zip\" : 97451,\n" +
                "  \"phone\" : \"phone1\",\n" +
                "  \"email\" : \"mail.test1@email.com\"\n" +
                "}");*/

    }

}
