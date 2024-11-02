package com.fractalis.greentoolswebservice.cropStatus.application.internal.queryservices;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates.Plant;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.PlantQueryService;
import com.fractalis.greentoolswebservice.cropStatus.infrastructure.persistence.jpa.repositories.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantQueryServiceImpl implements PlantQueryService {
    private final PlantRepository plantRepository;

    @Autowired
    public PlantQueryServiceImpl(PlantRepository plantRepository){
        this.plantRepository = plantRepository;
    }
    @Override
    public Optional<Plant> getPlantById(Long plantId) {
        return plantRepository.findById(plantId);
    }

    @Override
    public List<Plant> getPlantsByStationId(Long stationId) {
        return plantRepository.findByStationId(stationId);
    }

    @Override
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }
}
