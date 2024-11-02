package com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.valueObjects.PlantName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plantId")
    private Long id;

    @Column(name="station_id")
    private Long stationId;

    @Embedded
    @Column(name="plant_name")
    private PlantName plantName;

    @Column(name="plant_image")
    private String plantImage;

    public Plant(Long stationId, String plantName, String plantImage){
        this.stationId = stationId;
        this.plantName = new PlantName(plantName);
        this.plantImage = plantImage;
    }
}
