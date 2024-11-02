package com.fractalis.greentoolswebservice.inventory.domain.services;

import com.fractalis.greentoolswebservice.inventory.domain.model.aggregates.Inventory;


import java.util.List;
import java.util.Optional;

public interface InventoryQueryService {
    List<Inventory> getAllInventories();
    Optional<Inventory> getInventoryById(Long id);
    List<Inventory> getInventoryByUserId(Long id);
}