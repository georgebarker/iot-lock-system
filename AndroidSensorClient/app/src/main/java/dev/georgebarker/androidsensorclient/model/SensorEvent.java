package dev.georgebarker.androidsensorclient.model;

import java.sql.Timestamp;

public class SensorEvent {

    private int SensorEventId;
    private String tagId;
    private int roomNumber;
    private Timestamp timestamp;
    private boolean successful;

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

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
                ", roomNumber=" + roomNumber +
                ", timestamp=" + timestamp +
                ", successful=" + successful +
                '}';
    }
}
