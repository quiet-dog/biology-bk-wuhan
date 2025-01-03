package com.biology.domain.manage.environment.dto;

import lombok.Data;

@Data
public class EnvironmentStatisticsDTO {
    private String unit;
    private String dataTime;
    private double value;
}
