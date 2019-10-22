package dev.georgebarker.lockmanager.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class BeanConfig {

    private static final Logger LOG = LogManager.getLogger(BeanConfig.class);

    @Autowired
    PropertyConfig propertyConfig;

    @Bean
    public MqttClient mqttClient() throws MqttException {
	MqttClient client;
	final String brokerUrl = getBrokerUrl();
	final String userId = getUserId();
	LOG.info("Setting up MQTT Client using Broker URL: {} and User ID: {}...", brokerUrl, userId);
	try {
	    client = new MqttClient(brokerUrl, userId);
	    client.connect();
	} catch (final MqttException e) {
	    LOG.error("Client could not be created using Broker URL: {} and User ID: {}, quitting application.",
		    brokerUrl, userId);
	    throw e;
	}

	LOG.info("Created MQTT Client successfully.");
	return client;
    }

    @Bean
    public Gson gson() {
	return new Gson();
    }

    private String getBrokerUrl() {
	return propertyConfig.getBrokerUrl();
    }

    private String getUserId() {
	return propertyConfig.getUserId();
    }

}
