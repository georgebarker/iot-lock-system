package dev.georgebarker.lockmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.georgebarker.lockmanager.model.Room;
import dev.georgebarker.lockmanager.service.RoomService;

@RestController
@RequestMapping("rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/{deviceId}")
    public List<Room> getRoomsForDeviceId(@PathVariable final String deviceId) {
	return roomService.getRoomsForDeviceId(deviceId);
    }

}
