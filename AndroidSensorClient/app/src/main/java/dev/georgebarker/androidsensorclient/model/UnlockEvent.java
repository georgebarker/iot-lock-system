package dev.georgebarker.androidsensorclient.model;

import java.util.Date;

public class UnlockEvent {
    private long timestampMillis;
    private String roomNumber;
    private String tagId;

    public UnlockEvent(String deviceId, String roomNumber) {
	this.timestampMillis = new Date().getTime();
	this.roomNumber = roomNumber;
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

    public String getRoomNumber() {
	return roomNumber;
    }

    public void setRoomNumber(final String roomNumber) {
	this.roomNumber = roomNumber;
    }

    public String getTagId() {
	return tagId;
    }

    public void setTagId(final String tagId) {
	this.tagId = tagId;
    }
}
