package dev.georgebarker.lockmanager.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tag_room_combination")
public class TagRoomCombination {

    private TagRoomCombinationId tagRoomCombinationId;
    private boolean disabled;

    @EmbeddedId
    public TagRoomCombinationId getTagSensorLockCombinationId() {
	return tagRoomCombinationId;
    }

    public void setTagSensorLockCombinationId(final TagRoomCombinationId tagRoomCombinationId) {
	this.tagRoomCombinationId = tagRoomCombinationId;
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
	return "TagRoomCombination [tagRoomCombinationId=" + tagRoomCombinationId + ", disabled=" + disabled + "]";
    }

}
