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

}
