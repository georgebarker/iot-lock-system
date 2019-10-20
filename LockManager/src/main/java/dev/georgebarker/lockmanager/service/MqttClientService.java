package dev.georgebarker.lockmanager.service;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MqttClientService {
    void setupMqttClient() throws MqttException;

    void subscribeToTopic(final MqttCallback callback, final String topicName) throws MqttException;

    void publishMessageToTopic(MqttMessage message, String topicName) throws MqttException;
}
