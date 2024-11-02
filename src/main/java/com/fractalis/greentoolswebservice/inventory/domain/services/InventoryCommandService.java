package com.fractalis.greentoolswebservice.inventory.domain.services;

import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.inventory.domain.model.aggregates.Inventory;

import java.util.Date;

public interface InventoryCommandService {
    Inventory createInventory(Long user, String stationName, String description, Date endDate, String stationImage);
    void updateInventory(Long inventoryId, String stationName, String description, String stationImage);
    void deleteInventory(Long inventoryId);
}
