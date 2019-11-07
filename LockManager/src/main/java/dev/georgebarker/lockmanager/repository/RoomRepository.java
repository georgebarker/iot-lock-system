package dev.georgebarker.lockmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.georgebarker.lockmanager.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room findBySensorSerialNumber(int sensorSerialNumber);
}
