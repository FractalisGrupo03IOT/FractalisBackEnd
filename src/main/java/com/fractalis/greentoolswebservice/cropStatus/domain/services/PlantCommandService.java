package com.fractalis.greentoolswebservice.cropStatus.domain.services;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates.Plant;

public interface PlantCommandService {
    Plant createPlant(Long stationId, String name, String plantImage);
    void deletePlant(Long plantId);
}
