package com.fractalis.greentoolswebservice.cropStatus.interfaces.rest;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.entities.CropData;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.CropDataCommandService;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.CropDataQueryService;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.CreateCropDataResource;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.CropDataResource;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.transform.CreateCropDataResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "**", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Crop data", description = "All crop data related endpoints")
public class CropDataController {
    private final CropDataCommandService cropDataCommandService;
    private final CropDataQueryService cropDataQueryService;

    /**
     * Constructor
     *
     * @param cropDataCommandService The {@link CropDataCommandService} service
     * @param cropDataQueryService The {@link CropDataQueryService} service
     */
    @Autowired
    public CropDataController(CropDataCommandService cropDataCommandService, CropDataQueryService cropDataQueryService){
        this.cropDataCommandService = cropDataCommandService;
        this.cropDataQueryService = cropDataQueryService;
    }

    /**
     * Create crop data
     *
     * @param cropDataResource Crop data body
     * @return The just created {@link CropDataResource} crop data resource
     */
    @Operation(summary = "Create crop data")
    @Parameters({@Parameter(name = "crop data", description = "Crop data body", required = true)})
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Crop data created")})
    @PostMapping("/cropData")
    public ResponseEntity<CropDataResource> createCropData(@RequestBody CreateCropDataResource cropDataResource){
        CropData cropData = this.cropDataCommandService.createCropData(cropDataResource.plantId(), cropDataResource.humidity(), cropDataResource.temperature(), cropDataResource.uv());
        CropDataResource dataResource = CreateCropDataResourceFromEntityAssembler.toResourceFromEntity(cropData);
        return new ResponseEntity<>(dataResource, HttpStatus.CREATED);
    }

    /**
     * Get a list of crop data by plant id
     *
     * @param plantId The plant id
     * @return The list of {@link CropDataResource} crop data resources
     */
    @Operation(summary = "Get crop data list by plant id")
    @Parameters(@Parameter(name = "plantId", description = "Plant id", required = true))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Crop data founded")})
    @GetMapping("/cropData/{plantId}")
    public ResponseEntity<List<CropDataResource>> getCropDataByStationId(@PathVariable Long plantId){
        List<CropData> cropDataList = this.cropDataQueryService.getCropDataByStationId(plantId);
        List<CropDataResource> cropDataResourceList = cropDataList.stream().map(cropData ->
                CreateCropDataResourceFromEntityAssembler.toResourceFromEntity(cropData)).collect(Collectors.toList());
        return new ResponseEntity<>(cropDataResourceList, HttpStatus.OK);
    }

    /**
     * Get a list of the last month crop data by plant id
     *
     * @param plantId The plant id
     * @return The list of {@link CropDataResource} crop data resources
     */
    @Operation(summary = "Get last month crop data list by plant id")
    @Parameters(@Parameter(name = "plantId", description = "Plant id", required = true))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Crop data founded")})
    @GetMapping("/lastMonthCropData/{plantId}")
    public ResponseEntity<List<CropDataResource>> getLastMonthCropDataByStationId(@PathVariable Long plantId){
        List<CropData> cropDataList = this.cropDataQueryService.getLastMonthCropDataByStationId(plantId);
        List<CropDataResource> cropDataResourceList = cropDataList.stream().map(cropData ->
                CreateCropDataResourceFromEntityAssembler.toResourceFromEntity(cropData)).collect(Collectors.toList());
        return new ResponseEntity<>(cropDataResourceList, HttpStatus.OK);
    }
}
