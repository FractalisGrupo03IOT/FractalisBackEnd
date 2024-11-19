package com.fractalis.greentoolswebservice.cropStatus.infrastructure.persistence.jpa.repositories;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.entities.CropData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CropDataRepository extends JpaRepository<CropData, Long> {
    List<CropData> findByPlantId(Long plantId);

    @Query("SELECT c FROM CropData c WHERE c.plantId = :plantId AND c.dataDate >= :dateThreshold")
    List<CropData> findRecentCropsByPlantId(@Param("plantId") Long plantId, @Param("dateThreshold") Timestamp dateThreshold);
}
