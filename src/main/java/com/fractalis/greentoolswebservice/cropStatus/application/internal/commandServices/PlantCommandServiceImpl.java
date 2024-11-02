package com.fractalis.greentoolswebservice.cropStatus.application.internal.commandServices;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates.Plant;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.PlantCommandService;
import com.fractalis.greentoolswebservice.cropStatus.infrastructure.persistence.jpa.repositories.PlantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PlantCommandServiceImpl implements PlantCommandService {
    private final PlantRepository plantRepository;

    @Autowired
    public PlantCommandServiceImpl(PlantRepository plantRepository){
        this.plantRepository = plantRepository;
    }
    @Override
    public Plant createPlant(Long stationId, String name, String plantImage) {
        Plant plant = new Plant(stationId, name, plantImage);
        return plantRepository.save(plant);
    }

    @Override
    public void deletePlant(Long plantId) {
        plantRepository.deleteById(plantId);
    }
}
