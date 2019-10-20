package dev.georgebarker.lockmanager.listener;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.georgebarker.lockmanager.config.PropertyConfig;
import dev.georgebarker.lockmanager.service.MqttClientService;
import dev.georgebarker.lockmanager.service.SensorEventService;

@Service
public class SensorEventListenerImpl extends BaseMqttListener implements SensorEventListener {

    private static final Logger LOG = LogManager.getLogger(SensorEventListenerImpl.class);

    @Autowired
    PropertyConfig propertyConfig;

    @Autowired
    SensorEventService sensorEventService;

    @Autowired
    MqttClientService mqttClientService;

    @Override
    @PostConstruct
    public void subscribeToTopic() throws MqttException {
	final String topicName = getTopicName();
	LOG.info("Attempting to subscribe to topic: {}...", topicName);
	try {
	    mqttClientService.subscribeToTopic(this, topicName);
	    LOG.info("Subscribed to topic {}, listening for messages...", topicName);
	} catch (final MqttException e) {
	    LOG.error("Failed to subscribe to topic {}.", topicName);
	}
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) throws Exception {
	LOG.info("Received {} on topic {}, processing sensor event message", message, topic);
	sensorEventService.processSensorEventMessage(message);
    }

    private String getTopicName() {
	return propertyConfig.getSensorClientTopicName();
    }

}
