package dev.georgebarker.sensorclient.publisher;

import org.eclipse.paho.client.mqttv3.MqttException;

import dev.georgebarker.sensorclient.model.SensorEvent;

public interface SensorEventPublisher {
    void setupTopic() throws MqttException;

    void publish(SensorEvent sensorEvent);
}
