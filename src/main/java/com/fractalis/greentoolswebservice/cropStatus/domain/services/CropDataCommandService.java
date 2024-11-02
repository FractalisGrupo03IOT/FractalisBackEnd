package com.fractalis.greentoolswebservice.cropStatus.domain.services;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.entities.CropData;

public interface CropDataCommandService {
    CropData createCropData(Long plantId, Double humidity, Double temperature, Double uv);
}
