package dev.georgebarker.lockmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room")
public class Room {

    private int roomNumber;
    private int sensorSerialNumber;
    private int lockSerialNumber;

    public Room() {
	// Default constructor
    }

    public Room(final int roomNumber, final int sensorSerialNumber, final int lockSerialNumber) {
	this.roomNumber = roomNumber;
	this.sensorSerialNumber = sensorSerialNumber;
	this.lockSerialNumber = lockSerialNumber;
    }

    @Id
    @Column(name = "room_number")
    public int getRoomNumber() {
	return roomNumber;
    }

    public void setRoomNumber(final int roomNumber) {
	this.roomNumber = roomNumber;
    }

    @Column(name = "sensor_serial_number")
    public int getSensorSerialNumber() {
	return sensorSerialNumber;
    }

    public void setSensorSerialNumber(final int sensorSerialNumber) {
	this.sensorSerialNumber = sensorSerialNumber;
    }

    @Column(name = "lock_serial_number")
    public int getLockSerialNumber() {
	return lockSerialNumber;
    }

    public void setLockSerialNumber(final int lockSerialNumber) {
	this.lockSerialNumber = lockSerialNumber;
    }

    @Override
    public String toString() {
	return "Room [roomNumber=" + roomNumber + ", sensorSerialNumber=" + sensorSerialNumber + ", lockSerialNumber="
		+ lockSerialNumber + "]";
    }

}
