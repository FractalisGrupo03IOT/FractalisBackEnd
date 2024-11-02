package com.fractalis.greentoolswebservice.inventory.interfaces.rest.resources;

import java.util.Date;

public record CreateInventoryResource(Long userId, String stationName, String description, Date endDate, String stationImage) {
}
