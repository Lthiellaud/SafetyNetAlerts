package com.safetynet.safetynetalerts.service;

/**
 * To load the input data
 */
public interface LoadFileService {
    /**
     *  Reads the file data.json via an InputStream and save each node in the corresponding entity.
     */
    void execute();
}
