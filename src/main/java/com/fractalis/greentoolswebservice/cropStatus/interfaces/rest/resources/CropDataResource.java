package com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources;

public record CropDataResource(Long id, Long plantId, Double humidity, Double temperature, Double uv, String dataDate) {
}
