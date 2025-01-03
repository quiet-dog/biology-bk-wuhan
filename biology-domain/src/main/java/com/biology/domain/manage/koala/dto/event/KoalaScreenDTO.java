package com.biology.domain.manage.koala.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KoalaScreenDTO {
    @JsonProperty("camera_position")
    private String cameraPosition;
}
