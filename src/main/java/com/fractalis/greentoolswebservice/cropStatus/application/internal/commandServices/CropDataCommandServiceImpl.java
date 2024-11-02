package com.fractalis.greentoolswebservice.cropStatus.application.internal.commandServices;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.entities.CropData;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.CropDataCommandService;
import com.fractalis.greentoolswebservice.cropStatus.infrastructure.persistence.jpa.repositories.CropDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CropDataCommandServiceImpl implements CropDataCommandService {
    private final CropDataRepository cropDataRepository;

    @Autowired
    public CropDataCommandServiceImpl(CropDataRepository cropDataRepository){
        this.cropDataRepository = cropDataRepository;
    }

    @Override
    public CropData createCropData(Long plantId, Double humidity, Double temperature, Double uv) {
        CropData cropData = new CropData(plantId, humidity, temperature, uv);
        return cropDataRepository.save(cropData);
    }
}
