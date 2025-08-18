package com.biology.domain.manage.smThreshold.dto;

import lombok.Data;

@Data
public class SmThresholdDTO {
    private Long smDeviceId;

    private double min;

    private double max;

    private String type;

    private String level;
}
