package com.fractalis.greentoolswebservice.inventory.interfaces.rest;


import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.account.domain.services.UserQueryService;
import com.fractalis.greentoolswebservice.inventory.domain.model.aggregates.Inventory;
import com.fractalis.greentoolswebservice.inventory.domain.services.InventoryCommandService;
import com.fractalis.greentoolswebservice.inventory.domain.services.InventoryQueryService;
import com.fractalis.greentoolswebservice.inventory.interfaces.rest.resources.CreateInventoryResource;
import com.fractalis.greentoolswebservice.inventory.interfaces.rest.resources.InventoryResource;
import com.fractalis.greentoolswebservice.inventory.interfaces.rest.transform.CreateInventoryResourceFromEntityAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "**", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class InventoryController {

    private final InventoryCommandService inventoryCommandService;
    private final InventoryQueryService inventoryQueryService;
    private final UserQueryService userQueryService;

    @Autowired
    public InventoryController(InventoryCommandService inventoryCommandService, InventoryQueryService inventoryQueryService, UserQueryService userQueryService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
        this.userQueryService = userQueryService;
    }

    @GetMapping("/stations")
    public ResponseEntity<List<InventoryResource>> getAllInventories() {
        List<Inventory> inventories = inventoryQueryService.getAllInventories();
        List<InventoryResource> inventoryResources = inventories.stream().map(inventory ->
                        CreateInventoryResourceFromEntityAssembler.toResourceFromEntity(inventory)
                ).collect(Collectors.toList());
        return new ResponseEntity<>(inventoryResources, HttpStatus.OK);
    }

    @GetMapping("/station/{id}")
    public ResponseEntity<InventoryResource> getInventoryById(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryQueryService.getInventoryById(id);
        InventoryResource inventoryResource = CreateInventoryResourceFromEntityAssembler.toResourceFromEntity(inventory.get());
        return new ResponseEntity<>(inventoryResource, HttpStatus.OK);
    }

    @GetMapping("/stations/userId/{userId}")
    public ResponseEntity<List<InventoryResource>> getInventoryByUserId(@PathVariable Long userId) {
        List<Inventory> inventories = inventoryQueryService.getInventoryByUserId(userId);
        List<InventoryResource> inventoryResources = inventories.stream().map(inventory ->
                CreateInventoryResourceFromEntityAssembler.toResourceFromEntity(inventory)
        ).collect(Collectors.toList());
        return new ResponseEntity<>(inventoryResources, HttpStatus.OK);
    }

    @PostMapping("/station")
    public ResponseEntity<Inventory> createInventory(@RequestBody CreateInventoryResource inventoryRequest) {
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

    // PUT: Actualizar un inventario existente
    @PutMapping("/station/{id}")
    public ResponseEntity<Void> updateInventory(@PathVariable Long id,
                                                @RequestParam String stationName,
                                                @RequestParam String description,
                                                @RequestParam String stationImage) {
        try {
            inventoryCommandService.updateInventory(id, stationName, description, stationImage);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Eliminar un inventario por su ID
    @DeleteMapping("/station/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        try {
            inventoryCommandService.deleteInventory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}