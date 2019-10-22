package dev.georgebarker.lockclient.listener;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.georgebarker.lockclient.config.PropertyConfig;
import dev.georgebarker.lockclient.service.SensorEventService;

@Service
public class SensorEventListenerImpl extends BaseMqttListener implements SensorEventListener {

    private static final Logger LOG = LogManager.getLogger(SensorEventListenerImpl.class);

    @Autowired
    private PropertyConfig propertyConfig;

    @Autowired
    private SensorEventService sensorEventService;

    @Autowired
    private MqttClient mqttClient;

    @Override
    @PostConstruct
    public void subscribeToTopic() throws MqttException {
	final String topicName = getTopicName();
	LOG.info("Attempting to subscribe to topic: {}...", topicName);
	try {
	    mqttClient.setCallback(this);
	    mqttClient.subscribe(topicName);
	    LOG.info("Subscribed to topic {}, listening for messages...", topicName);
	} catch (final MqttException e) {
	    LOG.error("Failed to subscribe to topic {}.", topicName);
	}
    }

    @Override
    public void messageArrived(final String topic, final MqttMessage message) throws Exception {
	LOG.info("Received {} on topic {}, processing sensor event message...", message, topic);
	sensorEventService.processSensorEventMessage(message);
    }

    private String getTopicName() {
	return propertyConfig.getLockClientTopicName();
    }

}
