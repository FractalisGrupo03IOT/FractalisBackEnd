package com.fractalis.greentoolswebservice.cropStatus.interfaces.rest;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates.Plant;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.PlantCommandService;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.PlantQueryService;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.CreatePlantResource;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.PlantResource;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.transform.CreatePlantResourceFromEntityAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PlantController {
    private final PlantCommandService plantCommandService;
    private final PlantQueryService plantQueryService;

    @Autowired
    public PlantController(PlantCommandService plantCommandService, PlantQueryService plantQueryService){
        this.plantCommandService = plantCommandService;
        this.plantQueryService = plantQueryService;
    }

    @GetMapping("/plant/{plantId}")
    public ResponseEntity<PlantResource> getPlantById(@PathVariable Long plantId){
        Optional<Plant> plant = this.plantQueryService.getPlantById(plantId);
        PlantResource plantResource = CreatePlantResourceFromEntityAssembler.toResourceFromEntity(plant.get());
        return new ResponseEntity<>(plantResource, HttpStatus.OK);
    }

    @GetMapping("/plants/{stationId}")
    public ResponseEntity<List<PlantResource>> getPlantsByStationId(@PathVariable Long stationId){
        List<Plant> plants = this.plantQueryService.getPlantsByStationId(stationId);
        List<PlantResource> plantResources = plants.stream().map(plant ->
                CreatePlantResourceFromEntityAssembler.toResourceFromEntity(plant)).collect(Collectors.toList());
        return new ResponseEntity<>(plantResources, HttpStatus.OK);
    }

    @GetMapping("/plants")
    public ResponseEntity<List<PlantResource>> getAllPlants() {
        List<Plant> plants = this.plantQueryService.getAllPlants();
        List<PlantResource> plantResources = plants.stream().map(plant ->
                CreatePlantResourceFromEntityAssembler.toResourceFromEntity(plant)).collect(Collectors.toList());
        return new ResponseEntity<>(plantResources, HttpStatus.OK);
    }

    @PostMapping("/plant")
    public ResponseEntity<PlantResource> createUser(@RequestBody CreatePlantResource plantRequest) {
        Plant plant = this.plantCommandService.createPlant(plantRequest.stationId(), plantRequest.name(), plantRequest.plantImage());
        PlantResource plantResource = CreatePlantResourceFromEntityAssembler.toResourceFromEntity(plant);
        return new ResponseEntity<>(plantResource, HttpStatus.CREATED);
    }

    @DeleteMapping("/plant/{plantId}")
    public ResponseEntity<Void> deletePlant(@PathVariable Long plantId) {
        Optional<Plant> existingPlant = this.plantQueryService.getPlantById(plantId);
        if (existingPlant.isPresent()) {
            plantCommandService.deletePlant(plantId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
