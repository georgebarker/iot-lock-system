package dev.georgebarker.lockmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.georgebarker.lockmanager.model.TagRoomCombination;
import dev.georgebarker.lockmanager.model.TagRoomCombinationId;

@Repository
public interface TagSensorCombinationRepository extends JpaRepository<TagRoomCombination, TagRoomCombinationId> {
}
