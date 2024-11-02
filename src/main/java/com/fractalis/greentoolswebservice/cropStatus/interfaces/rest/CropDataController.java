package com.fractalis.greentoolswebservice.cropStatus.interfaces.rest;

import com.fractalis.greentoolswebservice.cropStatus.domain.model.entities.CropData;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.CropDataCommandService;
import com.fractalis.greentoolswebservice.cropStatus.domain.services.CropDataQueryService;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.CreateCropDataResource;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources.CropDataResource;
import com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.transform.CreateCropDataResourceFromEntityAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "**", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class CropDataController {
    private final CropDataCommandService cropDataCommandService;
    private final CropDataQueryService cropDataQueryService;

    @Autowired
    public CropDataController(CropDataCommandService cropDataCommandService, CropDataQueryService cropDataQueryService){
        this.cropDataCommandService = cropDataCommandService;
        this.cropDataQueryService = cropDataQueryService;
    }

    @PostMapping("/cropData")
    public ResponseEntity<CropDataResource> createCropData(@RequestBody CreateCropDataResource cropDataResource){
        CropData cropData = this.cropDataCommandService.createCropData(cropDataResource.plantId(), cropDataResource.humidity(), cropDataResource.temperature(), cropDataResource.uv());
        CropDataResource dataResource = CreateCropDataResourceFromEntityAssembler.toResourceFromEntity(cropData);
        return new ResponseEntity<>(dataResource, HttpStatus.CREATED);
    }

    @GetMapping("/cropData/{plantId}")
    public ResponseEntity<List<CropDataResource>> getCropDataByStationId(@PathVariable Long plantId){
        List<CropData> cropDataList = this.cropDataQueryService.getCropDataByStationId(plantId);
        List<CropDataResource> cropDataResourceList = cropDataList.stream().map(cropData ->
                CreateCropDataResourceFromEntityAssembler.toResourceFromEntity(cropData)).collect(Collectors.toList());
        return new ResponseEntity<>(cropDataResourceList, HttpStatus.OK);
    }

    @GetMapping("/lastMonthCropData/{plantId}")
    public ResponseEntity<List<CropDataResource>> getLastMonthCropDataByStationId(@PathVariable Long plantId){
        List<CropData> cropDataList = this.cropDataQueryService.getLastMonthCropDataByStationId(plantId);
        List<CropDataResource> cropDataResourceList = cropDataList.stream().map(cropData ->
                CreateCropDataResourceFromEntityAssembler.toResourceFromEntity(cropData)).collect(Collectors.toList());
        return new ResponseEntity<>(cropDataResourceList, HttpStatus.OK);
    }
}
