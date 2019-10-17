package dev.georgebarker.lockmanager.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * I am an abstract class that implements MqttCallback in order to simplify the
 * work that needs to be done in the implementation listeners. I provide a basic
 * implementation of connectionLost and deliveryComplete which the child class
 * can override if it wishes.
 *
 */
public abstract class BaseMqttListener implements MqttCallback {

    private static final Logger LOG = LogManager.getLogger(BaseMqttListener.class);

    @Override
    public void connectionLost(final Throwable cause) {
	LOG.warn("Connection lost.", cause);
    }

    @Override
    public void deliveryComplete(final IMqttDeliveryToken token) {
	LOG.info("Delivery complete, token: {}", token);

    }

    @Override
    public abstract void messageArrived(String topic, MqttMessage message) throws Exception;

}
