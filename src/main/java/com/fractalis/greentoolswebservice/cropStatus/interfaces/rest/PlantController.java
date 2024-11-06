package com.fractalis.greentoolswebservice.cropStatus.interfaces.rest;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.aggregates.Plant;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.PlantCommandService;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.PlantQueryService;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.CreatePlantResource;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.PlantResource;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.transform.CreatePlantResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "**", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Plants", description = "All plants related endpoints")
public class PlantController {
    private final PlantCommandService plantCommandService;
    private final PlantQueryService plantQueryService;

    /**
     * Constructor
     *
     * @param plantCommandService The {@link PlantCommandService} service
     * @param plantQueryService The {@link PlantQueryService} service
     */
    @Autowired
    public PlantController(PlantCommandService plantCommandService, PlantQueryService plantQueryService){
        this.plantCommandService = plantCommandService;
        this.plantQueryService = plantQueryService;
    }

    /**
     * Get a plant by its id
     *
     * @param plantId The plant id
     * @return The object {@link PlantResource} plant
     */
    @Operation(summary = "Get a plant by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plant founded")})
    @GetMapping("/plant/{plantId}")
    public ResponseEntity<PlantResource> getPlantById(
            @Parameter(name = "plantId", description = "Plant id", required = true) @PathVariable Long plantId){
        Optional<Plant> plant = this.plantQueryService.getPlantById(plantId);
        PlantResource plantResource = CreatePlantResourceFromEntityAssembler.toResourceFromEntity(plant.get());
        return new ResponseEntity<>(plantResource, HttpStatus.OK);
    }

    /**
     * Get a list of plants by the station id
     *
     * @param stationId The station id
     * @return The list of {@link PlantResource} plants
     */
    @Operation(summary = "Get plants by station id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plants founded")})
    @GetMapping("/plants/{stationId}")
    public ResponseEntity<List<PlantResource>> getPlantsByStationId(
            @Parameter(name = "stationId", description = "Station id", required = true) @PathVariable Long stationId){
        List<Plant> plants = this.plantQueryService.getPlantsByStationId(stationId);
        List<PlantResource> plantResources = plants.stream().map(
                CreatePlantResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return new ResponseEntity<>(plantResources, HttpStatus.OK);
    }

    /**
     * Get the list of plants
     *
     * @return The list of {@link PlantResource} plants
     */
    @Operation(summary = "Get all plants")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plants founded")})
    @GetMapping("/plants")
    public ResponseEntity<List<PlantResource>> getAllPlants() {
        List<Plant> plants = this.plantQueryService.getAllPlants();
        List<PlantResource> plantResources = plants.stream().map(
                CreatePlantResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return new ResponseEntity<>(plantResources, HttpStatus.OK);
    }

    /**
     * Create a plant
     *
     * @param plantRequest Plant body
     * @return The created object {@link PlantResource} plant
     */
    @Operation(summary = "Create a plant")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Plant created")})
    @PostMapping("/plant")
    public ResponseEntity<PlantResource> createUser(
            @RequestBody @Schema(description = "Plant body") CreatePlantResource plantRequest) {
        Plant plant = this.plantCommandService.createPlant(plantRequest.stationId(), plantRequest.name(), plantRequest.plantImage());
        PlantResource plantResource = CreatePlantResourceFromEntityAssembler.toResourceFromEntity(plant);
        return new ResponseEntity<>(plantResource, HttpStatus.CREATED);
    }

    /**
     * Delete a plant
     *
     * @param plantId The plant id
     * @return The object {@link HttpStatus} status
     */
    @Operation(summary = "Delete plant by station id")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Plants deleted")})
    @DeleteMapping("/plant/{plantId}")
    public ResponseEntity<Void> deletePlant(
            @Parameter(name = "plantId", description = "Plant id", required = true) @PathVariable Long plantId) {
        Optional<Plant> existingPlant = this.plantQueryService.getPlantById(plantId);
        if (existingPlant.isPresent()) {
            plantCommandService.deletePlant(plantId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
