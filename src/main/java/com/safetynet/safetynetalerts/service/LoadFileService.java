package com.safetynet.safetynetalerts.service;

public interface LoadFileService {
    /**
     *  Reads the file data.json via an InputStream and save each node in the corresponding entity.
     */
    void execute();
}
