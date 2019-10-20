package dev.georgebarker.lockmanager.publisher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import dev.georgebarker.lockmanager.config.PropertyConfig;
import dev.georgebarker.lockmanager.model.SensorEvent;
import dev.georgebarker.lockmanager.service.MqttClientService;

@Service
public class SensorEventPublisherImpl implements SensorEventPublisher {

    private static final Logger LOG = LogManager.getLogger(SensorEventPublisherImpl.class);

    @Autowired
    PropertyConfig propertyConfig;

    @Autowired
    MqttClientService mqttClientService;

    Gson gson = new Gson();

    @Override
    public void publish(final SensorEvent sensorEvent) {
	final String sensorEventJson = convertSensorEventToJson(sensorEvent);
	final String topicName = getTopicName();
	LOG.info("Publishing on topic: {} Sensor Event: {} using JSON: {}...", topicName, sensorEvent, sensorEventJson);
	try {

	    mqttClientService.publishMessageToTopic(new MqttMessage(sensorEventJson.getBytes()), topicName);
	} catch (final MqttException e) {
	    LOG.error("Failed to publish on topic: {} Sensor Event: {} using JSON: {}.", topicName, sensorEvent,
		    sensorEventJson);
	    return;
	}
	LOG.info("Successfully published on topic: {} Sensor Event: {} using JSON: {}.", topicName, sensorEvent,
		sensorEventJson);
    }

    private String convertSensorEventToJson(final SensorEvent sensorEvent) {
	return gson.toJson(sensorEvent);
    }

    private String getTopicName() {
	return propertyConfig.getLockClientTopicName();
    }

}
