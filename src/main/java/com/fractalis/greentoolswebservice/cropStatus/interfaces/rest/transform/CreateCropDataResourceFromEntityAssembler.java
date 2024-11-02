package com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.transform;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.entities.CropData;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.CropDataResource;

public class CreateCropDataResourceFromEntityAssembler {
    public static CropDataResource toResourceFromEntity(CropData entity) {
        return new CropDataResource(
                entity.getId(),
                entity.getPlantId(),
                entity.getHumidity().humidity(),
                entity.getTemperature().temperature(),
                entity.getUv().uv(),
                entity.getFormattedDate()
        );
    }
}
