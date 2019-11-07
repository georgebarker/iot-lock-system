package dev.georgebarker.androidsensorclient.model;

import java.util.Date;

public class UnlockEvent {
    private long timestampMillis;
    private int sensorSerialNumber;
    private String tagId;

    public UnlockEvent(String deviceId, int sensorId) {
	this.timestampMillis = new Date().getTime();
	this.sensorSerialNumber = sensorId;
	this.tagId = deviceId;
    }

    @Override
    public String toString() {
	return "UnlockEvent [timestampMillis=" + timestampMillis + ", sensorSerialNumber=" + sensorSerialNumber
		+ ", tagId=" + tagId + "]";
    }

    public long getTimestampMillis() {
	return timestampMillis;
    }

    public void setTimestampMillis(final long timestampMillis) {
	this.timestampMillis = timestampMillis;
    }

    public int getSensorSerialNumber() {
	return sensorSerialNumber;
    }

    public void setSensorSerialNumber(final int sensorSerialNumber) {
	this.sensorSerialNumber = sensorSerialNumber;
    }

    public String getTagId() {
	return tagId;
    }

    public void setTagId(final String tagId) {
	this.tagId = tagId;
    }
}
