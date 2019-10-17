package dev.georgebarker.sensorclient.service;

import com.phidget22.PhidgetException;

public interface SensorService {
    void connectToRFIDSensor() throws PhidgetException;
}
