package com.fractalis.greentoolswebservice.inventory.interfaces.rest.resources;

import java.util.Date;

public record InventoryResource(Long Id, Long userId, String stationName, String description,String stationImage, String startDate, String endDate) {
}
