package dev.georgebarker.lockmanager.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import dev.georgebarker.lockmanager.model.ClientSensorEvent;
import dev.georgebarker.lockmanager.model.SensorEvent;
import dev.georgebarker.lockmanager.model.TagSensorLockCombination;
import dev.georgebarker.lockmanager.model.TagSensorLockCombinationId;
import dev.georgebarker.lockmanager.publisher.SensorEventPublisher;
import dev.georgebarker.lockmanager.repository.SensorEventRepository;
import dev.georgebarker.lockmanager.repository.TagSensorCombinationRepository;

@Service
public class SensorEventServiceImpl implements SensorEventService {

    private static final Logger LOG = LogManager.getLogger(SensorEventServiceImpl.class);

    @Autowired
    private TagSensorCombinationRepository tagSensorCombinationRepository;

    @Autowired
    private SensorEventRepository sensorEventRepository;

    @Autowired
    private SensorEventPublisher sensorEventPublisher;

    @Autowired
    private Gson gson;

    @Override
    public void processSensorEventMessage(final MqttMessage message) {
	final ClientSensorEvent clientSensorEvent = transformMessageIntoClientSensorEvent(message);
	LOG.info("Transformed message into Client Sensor Event {}", clientSensorEvent);
	final TagSensorLockCombination tagSensorLockCombination = getTagSensorCombination(clientSensorEvent);

	SensorEvent sensorEvent;
	if (tagSensorLockCombination != null) {
	    LOG.info("Found Tag Sensor Lock Combination: {}, recording successful sensor event...",
		    tagSensorLockCombination);
	    final int lockSerialNumber = tagSensorLockCombination.getLockSerialNumber();
	    sensorEvent = recordSensorEvent(clientSensorEvent, lockSerialNumber, true);
	} else {
	    LOG.warn(
		    "No combination found for Tag ID: {} and Sensor Serial Number {}, recording unsuccessful sensor event...",
		    clientSensorEvent.getTagId(), clientSensorEvent.getSensorSerialNumber());
	    sensorEvent = recordSensorEvent(clientSensorEvent, 0, false);
	}

	LOG.info("Recorded sensor event, publishing sensor event...");
	sensorEventPublisher.publish(sensorEvent);

    }

    private ClientSensorEvent transformMessageIntoClientSensorEvent(final MqttMessage message) {
	return gson.fromJson(message.toString(), ClientSensorEvent.class);
    }

    private TagSensorLockCombination getTagSensorCombination(final ClientSensorEvent clientSensorEvent) {
	final TagSensorLockCombinationId id = new TagSensorLockCombinationId(clientSensorEvent.getTagId(),
		clientSensorEvent.getSensorSerialNumber());
	final Optional<TagSensorLockCombination> tagSensorCombination = tagSensorCombinationRepository.findById(id);

	if (tagSensorCombination.isPresent()) {
	    return tagSensorCombination.get();
	}

	return null;
    }

    private SensorEvent recordSensorEvent(final ClientSensorEvent clientSensorEvent, final int lockSerialNumber,
	    final boolean isSuccessful) {
	final SensorEvent sensorEvent = new SensorEvent(clientSensorEvent.getTagId(),
		clientSensorEvent.getSensorSerialNumber(), lockSerialNumber, isSuccessful);
	return sensorEventRepository.save(sensorEvent);
    }

}
