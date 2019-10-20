package dev.georgebarker.lockmanager.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tag_sensor_combination")
public class TagSensorCombination {

    private TagSensorCombinationId tagSensorCombinationId;
    private boolean disabled;

    @EmbeddedId
    public TagSensorCombinationId getTagSensorCombinationId() {
	return tagSensorCombinationId;
    }

    public void setTagSensorCombinationId(final TagSensorCombinationId tagSensorCombinationId) {
	this.tagSensorCombinationId = tagSensorCombinationId;
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
	return "TagSensorCombination [tagSensorCombinationId=" + tagSensorCombinationId + ", disabled=" + disabled
		+ "]";
    }

}
