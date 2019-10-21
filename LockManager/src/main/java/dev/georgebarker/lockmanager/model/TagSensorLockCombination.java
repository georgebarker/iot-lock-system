package dev.georgebarker.lockmanager.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tag_sensor_lock_combination")
public class TagSensorLockCombination {

    private TagSensorLockCombinationId tagSensorLockCombinationId;
    private int lockSerialNumber;
    private boolean disabled;

    @EmbeddedId
    public TagSensorLockCombinationId getTagSensorLockCombinationId() {
	return tagSensorLockCombinationId;
    }

    public void setTagSensorLockCombinationId(final TagSensorLockCombinationId tagSensorLockCombinationId) {
	this.tagSensorLockCombinationId = tagSensorLockCombinationId;
    }

    @Column(name = "lock_serial_number")
    public int getLockSerialNumber() {
	return lockSerialNumber;
    }

    public void setLockSerialNumber(final int lockSerialNumber) {
	this.lockSerialNumber = lockSerialNumber;
    }

    @Column(name = "disabled")
    public boolean isDisabled() {
	return disabled;
    }

    public void setDisabled(final boolean disabled) {
	this.disabled = disabled;
    }

    @Override
    public String toString() {
	return "TagSensorLockCombination [tagSensorLockCombinationId=" + tagSensorLockCombinationId
		+ ", lockSerialNumber=" + lockSerialNumber + ", disabled=" + disabled + "]";
    }

}
