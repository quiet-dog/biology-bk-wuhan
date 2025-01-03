package com.biology.domain.manage.environment.dto;

import java.util.List;

import lombok.Data;

@Data
public class EnvironmentEchartDTO {
    private String unit;
    private List<EnvironmentStatisticsDTO> data;
}
