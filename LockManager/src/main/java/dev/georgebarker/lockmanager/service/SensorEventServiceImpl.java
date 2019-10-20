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
import dev.georgebarker.lockmanager.model.TagSensorCombination;
import dev.georgebarker.lockmanager.model.TagSensorCombinationId;
import dev.georgebarker.lockmanager.publisher.SensorEventPublisher;
import dev.georgebarker.lockmanager.repository.SensorEventRepository;
import dev.georgebarker.lockmanager.repository.TagSensorCombinationRepository;

@Service
public class SensorEventServiceImpl implements SensorEventService {

    @Autowired
    TagSensorCombinationRepository tagSensorCombinationRepository;

    @Autowired
    SensorEventRepository sensorEventRepository;

    @Autowired
    SensorEventPublisher sensorEventPublisher;

    Gson gson = new Gson();

    private static final Logger LOG = LogManager.getLogger(SensorEventServiceImpl.class);

    @Override
    public void processSensorEventMessage(final MqttMessage message) {
	final ClientSensorEvent clientSensorEvent = transformMessageIntoClientSensorEvent(message);
	LOG.info("Transformed message into Client Sensor Event {}", clientSensorEvent);
	final TagSensorCombination tagSensorCombination = getTagSensorCombination(clientSensorEvent);

	if (tagSensorCombination == null) {
	    LOG.warn(
		    "No combination found for Tag ID: {} and Sensor Serial Number {}, recording unsuccessful sensor event and terminating processing.",
		    clientSensorEvent.getTagId(), clientSensorEvent.getSensorSerialNumber());
	    recordSensorEvent(clientSensorEvent, false);
	    return;
	}

	LOG.info("Found Tag Sensor Combination: {}, recording successful sensor event...", tagSensorCombination);
	final SensorEvent sensorEvent = recordSensorEvent(clientSensorEvent, true);

	LOG.info("Recorded successful sensor event, publishing sensor event...");
	sensorEventPublisher.publish(sensorEvent);

    }

    private ClientSensorEvent transformMessageIntoClientSensorEvent(final MqttMessage message) {
	return gson.fromJson(message.toString(), ClientSensorEvent.class);
    }

    private TagSensorCombination getTagSensorCombination(final ClientSensorEvent clientSensorEvent) {
	final TagSensorCombinationId id = new TagSensorCombinationId(clientSensorEvent.getTagId(),
		clientSensorEvent.getSensorSerialNumber());
	final Optional<TagSensorCombination> tagSensorCombination = tagSensorCombinationRepository.findById(id);

	if (tagSensorCombination.isPresent()) {
	    return tagSensorCombination.get();
	}

	return null;
    }

    private SensorEvent recordSensorEvent(final ClientSensorEvent clientSensorEvent, final boolean isSuccessful) {
	final SensorEvent sensorEvent = new SensorEvent(clientSensorEvent.getTagId(),
		clientSensorEvent.getSensorSerialNumber(), isSuccessful);
	return sensorEventRepository.save(sensorEvent);
    }

}
