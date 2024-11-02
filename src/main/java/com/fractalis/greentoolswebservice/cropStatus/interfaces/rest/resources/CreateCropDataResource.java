package com.fractalis.greentoolswebservice.cropStatus.interfaces.rest.resources;

public record CreateCropDataResource(Long plantId, Double humidity, Double temperature, Double uv) {
}
