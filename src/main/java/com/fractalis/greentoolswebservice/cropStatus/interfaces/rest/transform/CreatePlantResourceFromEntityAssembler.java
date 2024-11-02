package com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.transform;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates.Plant;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.PlantResource;

public class CreatePlantResourceFromEntityAssembler {
    public static PlantResource toResourceFromEntity(Plant entity) {
        return new PlantResource(
                entity.getId(),
                entity.getStationId(),
                entity.getPlantName().plantName(),
                entity.getPlantImage());
    }
}
