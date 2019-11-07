package dev.georgebarker.lockmanager.model;

public class ClientSensorEvent {

    private long timestampMillis;
    private int sensorSerialNumber;
    private String tagId;
    private int roomNumber;

    @Override
    public String toString() {
	return "ClientSensorEvent [timestampMillis=" + timestampMillis + ", sensorSerialNumber=" + sensorSerialNumber
		+ ", tagId=" + tagId + ", roomNumber=" + roomNumber + "]";
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

    public int getRoomNumber() {
	return roomNumber;
    }

    public void setRoomNumber(final int roomNumber) {
	this.roomNumber = roomNumber;
    }

}
