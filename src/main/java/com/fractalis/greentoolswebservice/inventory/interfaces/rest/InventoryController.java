package com.fractalis.greentoolswebservice.inventory.interfaces.rest;


import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.account.domain.services.UserQueryService;
import com.fractalis.greentoolswebservice.inventory.domain.model.aggregates.Inventory;
import com.fractalis.greentoolswebservice.inventory.domain.services.InventoryCommandService;
import com.fractalis.greentoolswebservice.inventory.domain.services.InventoryQueryService;
import com.fractalis.greentoolswebservice.inventory.interfaces.rest.resources.CreateInventoryResource;
import com.fractalis.greentoolswebservice.inventory.interfaces.rest.resources.InventoryResource;
import com.fractalis.greentoolswebservice.inventory.interfaces.rest.transform.CreateInventoryResourceFromEntityAssembler;
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
@Tag(name = "Stations", description = "All station related endpoints")
public class InventoryController {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;
    private final UserQueryService userQueryService;

    /**
     * Constructor
     *
     * @param inventoryCommandService The {@link InventoryCommandService} service
     * @param inventoryQueryService The {@link InventoryQueryService} service
     * @param userQueryService The {@link UserQueryService} service
     */
    @Autowired
    public InventoryController(InventoryCommandService inventoryCommandService, InventoryQueryService inventoryQueryService, UserQueryService userQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
        this.userQueryService = userQueryService;
    }

    /**
     * Get all the stations
     *
     * @return The list of {@link InventoryResource} stations
     */
    @Operation(summary = "Get all stations")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Stations founded")})
    @GetMapping("/stations")
    public ResponseEntity<List<InventoryResource>> getAllInventories() {
        List<Inventory> inventories = inventoryQueryService.getAllInventories();
        List<InventoryResource> inventoryResources = inventories.stream().map(
                CreateInventoryResourceFromEntityAssembler::toResourceFromEntity
                ).collect(Collectors.toList());
        return new ResponseEntity<>(inventoryResources, HttpStatus.OK);
    }

    /**
     * Get a station by id
     *
     * @param id The user id
     * @return The object {@link InventoryResource} station
     */
    @Operation(summary = "Get station by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Station founded")})
    @GetMapping("/station/{id}")
    public ResponseEntity<InventoryResource> getInventoryById(
            @Parameter(name = "id", description = "Plant id", required = true) @PathVariable Long id) {
        Optional<Inventory> inventory = inventoryQueryService.getInventoryById(id);
        InventoryResource inventoryResource = CreateInventoryResourceFromEntityAssembler.toResourceFromEntity(inventory.get());
        return new ResponseEntity<>(inventoryResource, HttpStatus.OK);
    }

    /**
     * Get a list of stations by user id
     *
     * @param userId The user id
     * @return The list of {@link InventoryResource} stations
     */
    @Operation(summary = "Get stations by user id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Stations founded")})
    @GetMapping("/stations/userId/{userId}")
    public ResponseEntity<List<InventoryResource>> getInventoryByUserId(
            @Parameter(name = "userId", description = "User id", required = true) @PathVariable Long userId) {
        List<Inventory> inventories = inventoryQueryService.getInventoryByUserId(userId);
        List<InventoryResource> inventoryResources = inventories.stream().map(
                CreateInventoryResourceFromEntityAssembler::toResourceFromEntity
        ).collect(Collectors.toList());
        return new ResponseEntity<>(inventoryResources, HttpStatus.OK);
    }

    /**
     * Create a station
     *
     * @param inventoryRequest Inventory body
     * @return The just created {@link Inventory} station
     */
    @Operation(summary = "Create a station")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Station created")})
    @PostMapping("/station")
    public ResponseEntity<Inventory> createInventory(
            @RequestBody @Schema(description = "Station body") CreateInventoryResource inventoryRequest) {
        Optional<User> user = userQueryService.getUserById(inventoryRequest.userId());
        if (user.isPresent()) {
            Inventory inventory = inventoryCommandService.createInventory(
                    inventoryRequest.userId(),
                    inventoryRequest.stationName(),
                    inventoryRequest.description(),
                    inventoryRequest.endDate(),
                    inventoryRequest.stationImage()
            );
            return new ResponseEntity<>(inventory, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Update a station
     *
     * @param id The station id
     * @param stationName The station name
     * @param description The station description
     * @param stationImage The station image
     * @return The object {@link HttpStatus} status
     */
    // PUT: Actualizar un inventario existente
    @Operation(summary = "Update a station")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Station updated")})
    @PutMapping("/station/{id}")
    public ResponseEntity<Void> updateInventory(
            @Parameter(name = "inventoryId", description = "Station id", required = true) @PathVariable Long id,
            @Parameter(name = "stationName", description = "Station name", required = true) @RequestParam String stationName,
            @Parameter(name = "description", description = "Station description", required = true) @RequestParam String description,
            @Parameter(name = "stationImage", description = "Station image", required = true) @RequestParam String stationImage) {
        try {
            inventoryCommandService.updateInventory(id, stationName, description, stationImage);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete a station
     *
     * @param id The station id
     * @return The object {@link HttpStatus} status
     */
    @Operation(summary = "Delete a station")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Station deleted")})
    @DeleteMapping("/station/{id}")
    public ResponseEntity<Void> deleteInventory(
            @Parameter(name = "inventoryId", description = "Station id", required = true) @PathVariable Long id) {
        try {
            inventoryCommandService.deleteInventory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}