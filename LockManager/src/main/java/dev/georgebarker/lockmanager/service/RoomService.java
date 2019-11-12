package dev.georgebarker.lockmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.georgebarker.lockmanager.model.Room;
import dev.georgebarker.lockmanager.model.TagRoomCombination;
import dev.georgebarker.lockmanager.repository.RoomRepository;
import dev.georgebarker.lockmanager.repository.TagRoomCombinationRepository;

@Service
public class RoomService {

    private static final Logger LOG = LogManager.getLogger(RoomService.class);
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    TagRoomCombinationRepository tagRoomCombinationRepository;

    public List<Room> getRoomsForDeviceId(final String deviceId) {
	final List<TagRoomCombination> combinations = tagRoomCombinationRepository
		.findByTagRoomCombinationIdTagId(deviceId);
	final List<Room> rooms = new ArrayList<>();
	for (final TagRoomCombination combination : combinations) {
	    final Room room = combination.getTagRoomCombinationId().getRoom();
	    LOG.info("Found room: {} for Device ID: {}", room, deviceId);
	    rooms.add(room);
	}
	return rooms;
    }
}
