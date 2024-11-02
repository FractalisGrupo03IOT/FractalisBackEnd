package com.fractalis.greentoolswebservice.inventory.interfaces.rest.transform;

import com.fractalis.greentoolswebservice.inventory.domain.model.aggregates.Inventory;
import com.fractalis.greentoolswebservice.inventory.interfaces.rest.resources.InventoryResource;

public class CreateInventoryResourceFromEntityAssembler {
    public static InventoryResource toResourceFromEntity(Inventory entity) {
        return new InventoryResource(
                entity.getId(),
                entity.getUserId(),
                entity.getStationName(),
                entity.getDescription(),
                entity.getStationImage(),
                entity.getFormattedStartDate(),
                entity.getFormattedEndDate());
    }
}
