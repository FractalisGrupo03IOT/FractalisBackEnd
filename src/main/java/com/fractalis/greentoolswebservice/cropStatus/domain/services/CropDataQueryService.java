package com.fractalis.greentoolswebservice.cropStatus.domain.services;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.entities.CropData;

import java.util.List;

public interface CropDataQueryService {
    List<CropData> getCropDataByStationId(Long stationId);
    List<CropData> getLastMonthCropDataByStationId(Long stationId);
}
