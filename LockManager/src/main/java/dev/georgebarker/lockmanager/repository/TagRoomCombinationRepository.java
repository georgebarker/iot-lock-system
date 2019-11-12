package dev.georgebarker.lockmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.georgebarker.lockmanager.model.TagRoomCombination;
import dev.georgebarker.lockmanager.model.TagRoomCombinationId;

@Repository
public interface TagRoomCombinationRepository extends JpaRepository<TagRoomCombination, TagRoomCombinationId> {
    List<TagRoomCombination> findByTagRoomCombinationIdTagId(String tagId);
}
