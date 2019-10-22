package dev.georgebarker.sensorclient.publisher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import dev.georgebarker.sensorclient.config.PropertyConfig;
import dev.georgebarker.sensorclient.model.SensorEvent;

@Service
public class SensorEventPublisherImpl implements SensorEventPublisher {

    private static final Logger LOG = LogManager.getLogger(SensorEventPublisherImpl.class);

    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    private MqttClient mqttClient;

    @Autowired
    private Gson gson;

    @Override
    public void publish(final SensorEvent sensorEvent) {
	final String sensorEventJson = convertSensorEventToJson(sensorEvent);
	final String topicName = getTopicName();
	LOG.info("Publishing on topic: {} Sensor Event: {} using JSON: {}...", topicName, sensorEvent, sensorEventJson);
	try {
	    publishMessageToTopic(new MqttMessage(sensorEventJson.getBytes()), topicName);
	} catch (final MqttException e) {
	    LOG.error("Failed to publish on topic: {} Sensor Event: {} using JSON: {}.", topicName, sensorEvent,
		    sensorEventJson);
	    return;
	}

	LOG.info("Successfully published on topic: {} Sensor Event: {} using JSON: {}.", topicName, sensorEvent,
		sensorEventJson);

    }

    private void publishMessageToTopic(final MqttMessage message, final String topicName) throws MqttException {
	final MqttTopic topic = mqttClient.getTopic(topicName);
	topic.publish(message);
    }

    private String convertSensorEventToJson(final SensorEvent sensorEvent) {
	return gson.toJson(sensorEvent);
    }

    private String getTopicName() {
	return propertyConfig.getTopicName();
    }

}
