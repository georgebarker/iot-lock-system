package dev.georgebarker.lockclient.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import dev.georgebarker.lockclient.model.SensorEvent;

@Service
public class SensorEventServiceImpl implements SensorEventService {

    private static final Logger LOG = LogManager.getLogger(SensorEventServiceImpl.class);

    @Autowired
    private LockMotorService lockMotorService;

    @Autowired
    private Gson gson;

    @Override
    public void processSensorEventMessage(final MqttMessage message) {
	final SensorEvent sensorEvent = transformMessageIntoSensorEvent(message);
	LOG.info("Transformed message into Sensor Event {}", sensorEvent);

	if (!sensorEvent.isSuccessful()) {
	    LOG.warn(
		    "Sensor Event was not successful and is therefore not permitted to open this lock, terminating process.");
	    return;
	}

	final int receivedSerialNumber = sensorEvent.getLockSerialNumber();
	final int lockSerialNumber = lockMotorService.getLockSerialNumber();
	if (receivedSerialNumber != lockSerialNumber) {
	    LOG.warn(
		    "This Sensor Event is not compatible with this lock, received {} but I have {}. Terminating process.",
		    receivedSerialNumber, lockSerialNumber);
	    return;
	}

	LOG.info("Sensor Event is valid, performing unlock...");
	lockMotorService.unlock();

    }

    private SensorEvent transformMessageIntoSensorEvent(final MqttMessage message) {
	return gson.fromJson(message.toString(), SensorEvent.class);
    }

}
