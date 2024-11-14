package com.fractalis.greentoolswebservice.cropStatus.domain.model.entities;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.valueObjects.Humidity;
import com.fractalis.greentoolswebservice.cropStatus.domain.model.valueObjects.Temperature;
import com.fractalis.greentoolswebservice.cropStatus.domain.model.valueObjects.UV;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class CropData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="plant_id")
    private Long plantId;

    @Embedded
    private Humidity humidity;

    @Embedded
    private Temperature temperature;

    @Embedded
    private UV uv;

    @Column(name = "data_date")
    private Date dataDate;

    public CropData(Long plantId, Double humidity, Double temperature, Double uv){
        this.plantId = plantId;
        this.humidity = new Humidity(humidity);
        this.temperature = new Temperature(temperature);
        this.uv = new UV(uv);
        this.dataDate = new Date();
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(dataDate);
    }
}
