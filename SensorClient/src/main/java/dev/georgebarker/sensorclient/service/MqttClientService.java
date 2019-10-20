package dev.georgebarker.sensorclient.service;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MqttClientService {
    void setupMqttClient() throws MqttException;

    void publishMessageToTopic(MqttMessage message, String topicName) throws MqttException;
}
