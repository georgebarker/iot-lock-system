package dev.georgebarker.lockmanager.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import dev.georgebarker.lockmanager.model.ClientSensorEvent;
import dev.georgebarker.lockmanager.model.Room;
import dev.georgebarker.lockmanager.model.SensorEvent;
import dev.georgebarker.lockmanager.model.TagRoomCombination;
import dev.georgebarker.lockmanager.model.TagRoomCombinationId;
import dev.georgebarker.lockmanager.publisher.SensorEventPublisher;
import dev.georgebarker.lockmanager.repository.RoomRepository;
import dev.georgebarker.lockmanager.repository.SensorEventRepository;
import dev.georgebarker.lockmanager.repository.TagRoomCombinationRepository;

@Service
public class SensorEventServiceImpl implements SensorEventService {

    private static final Logger LOG = LogManager.getLogger(SensorEventServiceImpl.class);

    @Autowired
    private TagRoomCombinationRepository tagRoomCombinationRepository;

    @Autowired
    private SensorEventRepository sensorEventRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SensorEventPublisher sensorEventPublisher;

    @Autowired
    private Gson gson;

    @Override
    public void processSensorEventMessage(final MqttMessage message) {
	final ClientSensorEvent clientSensorEvent = transformMessageIntoClientSensorEvent(message);
	LOG.info("Transformed message into Client Sensor Event {}", clientSensorEvent);

	Room room = null;

	// The Android client will allow the user to just specify their room number.
	if (clientEventSpecifiesRoomNumber(clientSensorEvent)) {
	    LOG.info("This request has specified a room number, no need to use Sensor Serial Number to retrieve it.");
	    room = findRoomByRoomNumber(clientSensorEvent.getRoomNumber());
	} else {
	    LOG.info("This request has not specified a room number, using the Sensor Serial Number to retrieve it...");
	    final int sensorSerialNumber = clientSensorEvent.getSensorSerialNumber();
	    room = roomRepository.findBySensorSerialNumber(sensorSerialNumber);
	}

	if (room == null) {
	    LOG.warn(
		    "No valid room found using the Client Sensor Event {}, recording and publishing unsuccessful event & terminating pipeline...",
		    clientSensorEvent);
	    final SensorEvent sensorEvent = recordSensorEvent(clientSensorEvent, clientSensorEvent.getRoomNumber(),
		    null, false, "The room does not exist.");
	    sensorEventPublisher.publish(sensorEvent);
	    return;
	}

	final TagRoomCombination tagRoomCombination = getTagRoomCombination(clientSensorEvent.getTagId(), room);

	SensorEvent sensorEvent;
	if (tagRoomCombination != null) {
	    LOG.info("Found Tag Room Combination: {}, recording successful sensor event...", tagRoomCombination);
	    sensorEvent = recordSensorEvent(clientSensorEvent, room.getRoomNumber(), room.getLockSerialNumber(), true,
		    "Room opened.");
	} else {
	    LOG.warn("No combination found for Tag ID: {} and Room {}, recording unsuccessful sensor event...",
		    clientSensorEvent.getTagId(), room.getRoomNumber());
	    sensorEvent = recordSensorEvent(clientSensorEvent, room.getRoomNumber(), null, false,
		    "Device/Tag does not have permission to open this room.");
	}

	LOG.info("Recorded sensor event, publishing sensor event...");
	sensorEventPublisher.publish(sensorEvent);

    }

    private ClientSensorEvent transformMessageIntoClientSensorEvent(final MqttMessage message) {
	return gson.fromJson(message.toString(), ClientSensorEvent.class);
    }

    private TagRoomCombination getTagRoomCombination(final String tagId, final Room room) {
	final TagRoomCombinationId id = new TagRoomCombinationId(tagId, room);
	final Optional<TagRoomCombination> tagSensorCombination = tagRoomCombinationRepository.findById(id);

	if (tagSensorCombination.isPresent()) {
	    return tagSensorCombination.get();
	}

	return null;
    }

    private SensorEvent recordSensorEvent(final ClientSensorEvent clientSensorEvent, final String roomNumber,
	    final Integer lockSerialNumber, final boolean isSuccessful, final String message) {
	final SensorEvent sensorEvent = new SensorEvent(clientSensorEvent.getTagId(), roomNumber, lockSerialNumber,
		isSuccessful, message);
	return sensorEventRepository.save(sensorEvent);
    }

    private boolean clientEventSpecifiesRoomNumber(final ClientSensorEvent clientSensorEvent) {
	final String roomNumber = clientSensorEvent.getRoomNumber();
	return roomNumber != null && !roomNumber.isEmpty() && !roomNumber.equals("0");
    }

    private Room findRoomByRoomNumber(final String roomNumber) {
	final Optional<Room> optionalRoom = roomRepository.findById(roomNumber);
	return optionalRoom.isPresent() ? optionalRoom.get() : null;
    }

}
