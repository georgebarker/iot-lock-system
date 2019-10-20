package dev.georgebarker.lockmanager.service;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.georgebarker.lockmanager.config.PropertyConfig;

@Service
public class MqttClientServiceImpl implements MqttClientService {

    private static final Logger LOG = LogManager.getLogger(MqttClientServiceImpl.class);

    @Autowired
    PropertyConfig propertyConfig;

    private MqttClient client;

    @PostConstruct
    @Override
    public void setupMqttClient() throws MqttException {
	final String brokerUrl = getBrokerUrl();
	final String userId = getUserId();
	LOG.info("Setting up MQTT Client using Broker URL: {} and User ID: {}...", brokerUrl, userId);
	try {
	    client = new MqttClient(getBrokerUrl(), getUserId());
	    client.connect();
	} catch (final MqttException e) {
	    LOG.error("Client could not be created using Broker URL: {} and User ID: {}, quitting application.",
		    brokerUrl, userId);
	    throw e;
	}

	LOG.info("Created MQTT Client successfully.");
    }

    @Override
    public void publishMessageToTopic(final MqttMessage message, final String topicName) throws MqttException {
	final MqttTopic topic = client.getTopic(topicName);
	topic.publish(message);

    }

    @Override
    public void subscribeToTopic(final MqttCallback callback, final String topicName) throws MqttException {
	client.setCallback(callback);
	client.subscribe(topicName);
    }

    private String getBrokerUrl() {
	return propertyConfig.getBrokerUrl();
    }

    private String getUserId() {
	return propertyConfig.getUserId();
    }

}
