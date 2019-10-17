package dev.georgebarker.sensorclient.model;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.phidget22.PhidgetException;
import com.phidget22.RFIDTagEvent;

public class SensorEvent {

    private static final Logger LOG = LogManager.getLogger(SensorEvent.class);

    private long timestampMillis;
    private int sensorSerialNumber;
    private String tagId;

    public SensorEvent(final RFIDTagEvent event) {
	this.timestampMillis = new Date().getTime();
	this.sensorSerialNumber = getEventSourceDeviceSerialNumber(event);
	this.tagId = event.getTag();
    }

    @Override
    public String toString() {
	return "SensorEvent [timestampMillis=" + timestampMillis + ", sensorSerialNumber=" + sensorSerialNumber
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

    private int getEventSourceDeviceSerialNumber(final RFIDTagEvent event) {
	try {
	    return event.getSource().getDeviceSerialNumber();
	} catch (final PhidgetException e) {
	    LOG.error("Couldn't find the source device serial number, returning 0", e);
	    return 0;
	}
    }
}
