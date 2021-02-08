package com.safetynet.safetynetalerts.configuration;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class.
 */
@Configuration
public class HttpTraceActuatorConfiguration {

    /**
     * Method to enable http trace.
     * @return in memory HttpTraceRepository (default)
     */
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
