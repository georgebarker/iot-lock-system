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
    private int sensorSerialNumber;
    private Timestamp timestamp;
    private boolean successful;

    public SensorEvent() {
	// Default constructor
    }

    public SensorEvent(final String tagId, final int sensorSerialNumber, final boolean successful) {
	this.tagId = tagId;
	this.sensorSerialNumber = sensorSerialNumber;
	this.successful = successful;
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

    @Column(name = "sensor_serial_number")
    public int getSensorSerialNumber() {
	return sensorSerialNumber;
    }

    public void setSensorSerialNumber(final int sensorSerialNumber) {
	this.sensorSerialNumber = sensorSerialNumber;
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

}
