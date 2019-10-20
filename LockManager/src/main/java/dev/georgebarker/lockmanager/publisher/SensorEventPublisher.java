package dev.georgebarker.lockmanager.publisher;

import dev.georgebarker.lockmanager.model.SensorEvent;

public interface SensorEventPublisher {
    void publish(SensorEvent sensorEvent);
}
