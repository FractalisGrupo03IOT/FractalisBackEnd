package com.fractalis.greentoolswebservice.cropStatus.domain.services;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates.Plant;

import java.util.List;
import java.util.Optional;

public interface PlantQueryService {
    Optional<Plant> getPlantById(Long plantId);
    List<Plant> getPlantsByStationId(Long stationId);
    List<Plant> getAllPlants();

}
