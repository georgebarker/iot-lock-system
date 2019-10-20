package dev.georgebarker.lockmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.georgebarker.lockmanager.model.TagSensorCombination;
import dev.georgebarker.lockmanager.model.TagSensorCombinationId;

@Repository
public interface TagSensorCombinationRepository extends JpaRepository<TagSensorCombination, TagSensorCombinationId> {
}
