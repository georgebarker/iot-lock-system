package dev.georgebarker.lockmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.georgebarker.lockmanager.model.SensorEvent;

@Repository
public interface SensorEventRepository extends JpaRepository<SensorEvent, Integer> {

}
