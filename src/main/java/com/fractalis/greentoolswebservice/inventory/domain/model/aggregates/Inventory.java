package com.fractalis.greentoolswebservice.inventory.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventoryId")
    private Long id;

    @Column(name = "stationName", nullable = false)
    private String stationName;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "station_image", nullable = false)
    private String stationImage;


    public Inventory(Long user, String stationName, String description, Date endDate, String stationImage) {
        this.userId = user;
        this.stationName = stationName;
        this.description = description;
        this.startDate = new Date();
        this.endDate = endDate;
        this.stationImage = stationImage;
    }

    public void updateInventory(String stationName, String description, String stationImage) {
        this.stationName = stationName;
        this.description = description;
        this.stationImage = stationImage;
    }

    public void updateEndDate(Date endDate){
        this.endDate = endDate;
    }

    public String getFormattedStartDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Solo día, mes y año
        return sdf.format(startDate);
    }

    public String getFormattedEndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Solo día, mes y año
        return sdf.format(endDate);
    }
}