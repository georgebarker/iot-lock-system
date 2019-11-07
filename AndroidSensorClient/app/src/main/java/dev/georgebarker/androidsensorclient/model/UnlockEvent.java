package dev.georgebarker.androidsensorclient.model;

import java.util.Date;

public class UnlockEvent {
    private long timestampMillis;
    private int roomNumber;
    private String tagId;

    public UnlockEvent(String deviceId, int sensorId) {
	this.timestampMillis = new Date().getTime();
	this.roomNumber = sensorId;
	this.tagId = deviceId;
    }

    @Override
    public String toString() {
	return "UnlockEvent [timestampMillis=" + timestampMillis + ", roomNumber=" + roomNumber
		+ ", tagId=" + tagId + "]";
    }

    public long getTimestampMillis() {
	return timestampMillis;
    }

    public void setTimestampMillis(final long timestampMillis) {
	this.timestampMillis = timestampMillis;
    }

    public int getRoomNumber() {
	return roomNumber;
    }

    public void setRoomNumber(final int roomNumber) {
	this.roomNumber = roomNumber;
    }

    public String getTagId() {
	return tagId;
    }

    public void setTagId(final String tagId) {
	this.tagId = tagId;
    }
}
