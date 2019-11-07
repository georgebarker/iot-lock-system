package dev.georgebarker.androidsensorclient.model;

import java.sql.Timestamp;

public class SensorEvent {

    private int SensorEventId;
    private String tagId;
    private int sensorSerialNumber;
    private int lockSerialNumber;
    private Timestamp timestamp;
    private boolean successful;

    public int getSensorEventId() {
	return SensorEventId;
    }

    public void setSensorEventId(final int sensorEventId) {
	SensorEventId = sensorEventId;
    }

    public String getTagId() {
	return tagId;
    }

    public void setTagId(final String tagId) {
	this.tagId = tagId;
    }

    public int getSensorSerialNumber() {
	return sensorSerialNumber;
    }

    public void setSensorSerialNumber(final int sensorSerialNumber) {
	this.sensorSerialNumber = sensorSerialNumber;
    }

    public int getLockSerialNumber() {
	return lockSerialNumber;
    }

    public void setLockSerialNumber(final int lockSerialNumber) {
	this.lockSerialNumber = lockSerialNumber;
    }

    public Timestamp getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(final Timestamp timestamp) {
	this.timestamp = timestamp;
    }

    public boolean isSuccessful() {
	return successful;
    }

    public void setSuccessful(final boolean successful) {
	this.successful = successful;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
                "SensorEventId=" + SensorEventId +
                ", tagId='" + tagId + '\'' +
                ", sensorSerialNumber=" + sensorSerialNumber +
                ", lockSerialNumber=" + lockSerialNumber +
                ", timestamp=" + timestamp +
                ", successful=" + successful +
                '}';
    }
}
