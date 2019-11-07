package dev.georgebarker.lockmanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class TagRoomCombinationId implements Serializable {

    private static final long serialVersionUID = 1773580746493371660L;

    private String tagId;
    private Room room;

    public TagRoomCombinationId() {
	// Default constructor
    }

    public TagRoomCombinationId(final String tagId, final Room room) {
	this.tagId = tagId;
	this.room = room;
    }

    @Column(name = "tag_id")
    public String getTagId() {
	return tagId;
    }

    public void setTagId(final String tagId) {
	this.tagId = tagId;
    }

    @ManyToOne
    @JoinColumn(name = "room_number")
    public Room getRoom() {
	return room;
    }

    public void setRoom(final Room room) {
	this.room = room;
    }

    @Override
    public String toString() {
	return "TagRoomCombinationId [tagId=" + tagId + ", room=" + room + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((room == null) ? 0 : room.hashCode());
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
	final TagRoomCombinationId other = (TagRoomCombinationId) obj;
	if (room == null) {
	    if (other.room != null) {
		return false;
	    }
	} else if (!room.equals(other.room)) {
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
