package dev.georgebarker.lockmanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TagSensorCombinationId implements Serializable {

    private static final long serialVersionUID = 1773580746493371660L;

    private String tagId;
    private int sensorSerialNumber;

    public TagSensorCombinationId() {
	// Default constructor
    }

    public TagSensorCombinationId(final String tagId, final int sensorSerialNumber) {
	this.tagId = tagId;
	this.sensorSerialNumber = sensorSerialNumber;
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

    @Override
    public String toString() {
	return "TagSensorCombinationId [tagId=" + tagId + ", sensorSerialNumber=" + sensorSerialNumber + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + sensorSerialNumber;
	result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
	return result;
    }

    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final TagSensorCombinationId other = (TagSensorCombinationId) obj;
	if (sensorSerialNumber != other.sensorSerialNumber) {
	    return false;
	}
	if (tagId == null) {
	    if (other.tagId != null) {
		return false;
	    }
	} else if (!tagId.equals(other.tagId)) {
	    return false;
	}
	return true;
    }
}
