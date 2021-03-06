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
    private String roomNumber;
    private Integer lockSerialNumber;
    private Timestamp timestamp;
    private boolean successful;
    private String message;

    public SensorEvent() {
	// Default constructor
    }

    public SensorEvent(final String tagId, final String roomNumber, final Integer lockSerialNumber,
	    final boolean successful, final String message) {
	this.tagId = tagId;
	this.roomNumber = roomNumber;
	this.lockSerialNumber = lockSerialNumber;
	this.successful = successful;
	this.timestamp = new Timestamp(System.currentTimeMillis());
	this.message = message;
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
    public String getRoomNumber() {
	return roomNumber;
    }

    public void setRoomNumber(final String roomNumber) {
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

    @Column(name = "message")
    public String getMessage() {
	return message;
    }

    public void setMessage(final String message) {
	this.message = message;
    }

    @Override
    public String toString() {
	return "SensorEvent [SensorEventId=" + SensorEventId + ", tagId=" + tagId + ", roomNumber=" + roomNumber
		+ ", lockSerialNumber=" + lockSerialNumber + ", timestamp=" + timestamp + ", successful=" + successful
		+ ", message=" + message + "]";
    }

}
