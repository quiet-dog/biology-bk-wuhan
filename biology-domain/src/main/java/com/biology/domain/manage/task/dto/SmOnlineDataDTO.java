package com.biology.domain.manage.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SmOnlineDataDTO {
    private String sn;
    private Boolean online;
    @JsonProperty("last_time")
    private Long lastTime;
}
