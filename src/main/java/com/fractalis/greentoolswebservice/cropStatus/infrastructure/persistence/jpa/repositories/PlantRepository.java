package com.fractalis.greentoolswebservice.cropStatus.infrastructure.persistence.jpa.repositories;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 
 
import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByStationId(Long stationId);
}
