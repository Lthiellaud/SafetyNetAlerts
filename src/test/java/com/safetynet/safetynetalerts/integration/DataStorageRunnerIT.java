package com.safetynet.safetynetalerts.integration;

import com.safetynet.safetynetalerts.DataStorageRunner;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = "command.line.runner.enabled=true")
@ActiveProfiles("test")
public class DataStorageRunnerIT {

    @SpyBean
    DataStorageRunner dataStorageRunner;

    @Test
    void whenContextLoads_thenDataStorageRunnerRun() {
        verify(dataStorageRunner, times(1)).run(any());
    }
}
