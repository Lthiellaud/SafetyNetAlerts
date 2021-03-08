package com.safetynet.safetynetalerts;

import com.safetynet.safetynetalerts.service.LoadFileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Command Line runner for executing method of loadFileService.
 */
@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class DataStorageRunner implements CommandLineRunner {

    private LoadFileService loadFileService;

    public DataStorageRunner(LoadFileService loadFileService) {
        this.loadFileService = loadFileService;
    }
    /**
     * Command Line runner for executing method of loadFileService.
     * @param args default argument
     */
    public void run(String... args) {

        loadFileService.execute();

    }
}
