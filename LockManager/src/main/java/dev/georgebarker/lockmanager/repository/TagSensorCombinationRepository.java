package dev.georgebarker.lockmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.georgebarker.lockmanager.model.TagSensorLockCombination;
import dev.georgebarker.lockmanager.model.TagSensorLockCombinationId;

@Repository
public interface TagSensorCombinationRepository extends JpaRepository<TagSensorLockCombination, TagSensorLockCombinationId> {
}
