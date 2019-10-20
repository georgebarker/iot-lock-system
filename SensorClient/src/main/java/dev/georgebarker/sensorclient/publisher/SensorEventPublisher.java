package dev.georgebarker.sensorclient.publisher;

import dev.georgebarker.sensorclient.model.SensorEvent;

public interface SensorEventPublisher {
    void publish(SensorEvent sensorEvent);
}
