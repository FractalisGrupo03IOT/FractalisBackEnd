package com.fractalis.greentoolswebservice.cropStatus.application.internal.queryservices;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.entities.CropData;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.CropDataQueryService;
import com.fractalis.greentoolswebservice.cropStatus.infrastructure.persistence.jpa.repositories.CropDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CropDataQueryServiceImpl implements CropDataQueryService {
    private final CropDataRepository cropDataRepository;

    @Autowired
    public CropDataQueryServiceImpl(CropDataRepository cropDataRepository){
        this.cropDataRepository = cropDataRepository;
    }
    @Override
    public List<CropData> getCropDataByStationId(Long plantId) {
        return cropDataRepository.findByPlantId(plantId);
    }

    @Override
    public List<CropData> getLastMonthCropDataByStationId(Long plantId) {
        LocalDateTime now = LocalDateTime.now();

        // Restar 24 horas
        LocalDateTime dateThreshold = now.minus(24, ChronoUnit.HOURS);

        // Convertir a Timestamp (precisión de microsegundos)
        Timestamp timestampThreshold = Timestamp.valueOf(dateThreshold);

        return cropDataRepository.findRecentCropsByPlantId(plantId, timestampThreshold);
    }
}
