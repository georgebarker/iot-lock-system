package dev.georgebarker.sensorclient.publisher;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
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
    private static final String FORWARD_SLASH = "/";

    private MqttTopic topic;

    @Autowired
    PropertyConfig propertyConfig;

    @PostConstruct
    @Override
    public void setupTopic() throws MqttException {
	LOG.info("Attempting to get topic: {} using Broker URL: {} and User ID: {}...", getTopic(), getBrokerUrl(),
		getUserId());
	final MqttClient client;
	try {
	    client = new MqttClient(getBrokerUrl(), getUserId());
	    final MqttConnectOptions options = new MqttConnectOptions();
	    options.setCleanSession(false); // don't know why
	    // options.setWill
	    client.connect(options);
	} catch (final MqttException e) {
	    LOG.error("Client could not be created using Broker URL: {} and User ID: {}, quitting application.",
		    getBrokerUrl(), getUserId());
	    throw e;
	}

	topic = client.getTopic(getTopic());
	LOG.info("Retrieved topic: {}.", getTopic());
    }

    @Override
    public void publish(final SensorEvent sensorEvent) {
	final String sensorEventJson = convertSensorEventToJson(sensorEvent);
	LOG.info("Publishing on topic: {} Sensor Event: {} using JSON: {}...", getTopic(), sensorEvent,
		sensorEventJson);
	try {

	    topic.publish(new MqttMessage(sensorEventJson.getBytes()));
	} catch (final MqttException e) {
	    LOG.error("Failed to publish on topic: {} Sensor Event: {} using JSON: {}.", getTopic(), sensorEvent,
		    sensorEventJson);
	    return;
	}

	LOG.info("Successfully published on topic: {} Sensor Event: {} using JSON: {}.", getTopic(), sensorEvent,
		sensorEventJson);

    }

    private String convertSensorEventToJson(final SensorEvent sensorEvent) {
	return new Gson().toJson(sensorEvent);
    }

    private String getBrokerUrl() {
	return propertyConfig.getBrokerUrl();
    }

    private String getUserId() {
	return propertyConfig.getUserId();
    }

    private String getTopic() {
	return propertyConfig.getUserId() + FORWARD_SLASH + propertyConfig.getTopicName();
    }

}
