package com.fractalis.greentoolswebservice.cropStatus.application.internal.queryservices;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.entities.CropData;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.CropDataQueryService;
import com.fractalis.greentoolswebservice.cropStatus.infrastructure.persistence.jpa.repositories.CropDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date dateThreshold = calendar.getTime();

        return cropDataRepository.findRecentCropsByPlantId(plantId, dateThreshold);
    }
}
