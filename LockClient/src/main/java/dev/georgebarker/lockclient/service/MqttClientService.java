package dev.georgebarker.lockclient.service;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;

public interface MqttClientService {
    void setupMqttClient() throws MqttException;

    void subscribeToTopic(final MqttCallback callback, final String topicName) throws MqttException;
}
