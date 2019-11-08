package dev.georgebarker.lockmanager.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sensor_event")
public class SensorEvent {

    private int SensorEventId;
    private String tagId;
    private Integer roomNumber;
    private Integer lockSerialNumber;
    private Timestamp timestamp;
    private boolean successful;

    public SensorEvent() {
	// Default constructor
    }

    public SensorEvent(final String tagId, final Integer roomNumber, final Integer lockSerialNumber,
	    final boolean successful) {
	this.tagId = tagId;
	this.roomNumber = roomNumber;
	this.lockSerialNumber = lockSerialNumber;
	this.successful = successful;
	this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Id
    @Column(name = "sensor_event_id")
    public int getSensorEventId() {
	return SensorEventId;
    }

    public void setSensorEventId(final int sensorEventId) {
	SensorEventId = sensorEventId;
    }

    @Column(name = "tag_id")
    public String getTagId() {
	return tagId;
    }

    public void setTagId(final String tagId) {
	this.tagId = tagId;
    }

    @Column(name = "room_number")
    public Integer getRoomNumber() {
	return roomNumber;
    }

    public void setRoomNumber(final Integer roomNumber) {
	this.roomNumber = roomNumber;
    }

    @Column(name = "lock_serial_number")
    public Integer getLockSerialNumber() {
	return lockSerialNumber;
    }

    public void setLockSerialNumber(final Integer lockSerialNumber) {
	this.lockSerialNumber = lockSerialNumber;
    }

    @Column(name = "event_timestamp")
    public Timestamp getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(final Timestamp timestamp) {
	this.timestamp = timestamp;
    }

    @Column(name = "success")
    public boolean isSuccessful() {
	return successful;
    }

    public void setSuccessful(final boolean successful) {
	this.successful = successful;
    }

    @Override
    public String toString() {
	return "SensorEvent [SensorEventId=" + SensorEventId + ", tagId=" + tagId + ", roomNumber=" + roomNumber
		+ ", lockSerialNumber=" + lockSerialNumber + ", timestamp=" + timestamp + ", successful=" + successful
		+ "]";
    }

}
