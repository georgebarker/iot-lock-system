package dev.georgebarker.lockmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.georgebarker.lockmanager.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    Room findBySensorSerialNumber(int sensorSerialNumber);
}
