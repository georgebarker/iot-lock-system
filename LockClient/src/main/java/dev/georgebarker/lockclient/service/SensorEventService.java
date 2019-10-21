package dev.georgebarker.lockclient.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface SensorEventService {
    void processSensorEventMessage(MqttMessage message);
}
