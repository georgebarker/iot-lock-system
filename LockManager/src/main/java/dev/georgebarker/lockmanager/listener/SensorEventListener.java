package dev.georgebarker.lockmanager.listener;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.georgebarker.lockmanager.config.PropertyConfig;

@Service
public class SensorEventListener extends BaseMqttListener {

    private static final Logger LOG = LogManager.getLogger(SensorEventListener.class);

    @Autowired
    PropertyConfig propertyConfig;

    @PostConstruct
    public void subscribeToTopic() throws MqttException {
	LOG.info("Attempting to subscribe to topic: {} using Broker URL: {} and User ID: {}...", getTopicName(),
		getBrokerUrl(), getUserId());
	final MqttClient client;
	try {
	    client = new MqttClient(getBrokerUrl(), getUserId());
	    client.setCallback(this);
	    client.connect();
	    client.subscribe(getTopicName());
	    LOG.info("Subscribed to topic {}, listening for messages...", getTopicName());
	} catch (final MqttException e) {
	    LOG.error(
		    "Client could not be created to connect to topic: {} using Broker URL: {} and User ID: {}, quitting application.",
		    getTopicName(), getBrokerUrl(), getUserId());
	    throw e;
	}
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) throws Exception {
	LOG.info("Received {} on topic {}", message, topic);
    }

    private String getBrokerUrl() {
	return propertyConfig.getBrokerUrl();
    }

    private String getUserId() {
	return propertyConfig.getUserId();
    }

    private String getTopicName() {
	return propertyConfig.getTopicName();
    }

}
