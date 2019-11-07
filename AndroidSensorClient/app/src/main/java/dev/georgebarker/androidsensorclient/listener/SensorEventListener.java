package dev.georgebarker.androidsensorclient.listener;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface SensorEventListener {
    void subscribeToTopic() throws MqttException;
}
