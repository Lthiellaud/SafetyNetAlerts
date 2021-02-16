package com.safetynet.safetynetalerts.util;


import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.safetynetalerts.service.LoadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertJsonUtil {

    private static Logger logger = LoggerFactory.getLogger(LoadFileService.class);

    private ObjectWriter writer;

    DefaultPrettyPrinter pp = new DefaultPrettyPrinter()
            .withoutSpacesInObjectEntries();
           // .withObjectIndenter(new DefaultIndenter("  ", "\n"));
            //.withArrayIndenter(new DefaultPrettyPrinter.NopIndenter());

    public ConvertJsonUtil() {
        this.writer = new ObjectMapper()
                .writer()
                .with(pp);
    }

    public String convertClassToJson(Object obj) {
        try {
            return writer.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("Problem while converting class to json", e);
            return null;
        }

    }
}
