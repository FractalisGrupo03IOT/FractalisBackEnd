package com.fractalis.greentoolswebservice.cropStatus.application.internal.commandEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CropCloudRequest {

    @JsonProperty("cropCloudId")
    private Long cropCloudId;

    public CropCloudRequest(Long cropCloudId) {
        this.cropCloudId = cropCloudId;
    }
}
