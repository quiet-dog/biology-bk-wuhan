package com.biology.domain.manage.healthy.dto;

import java.util.List;

import lombok.Data;

@Data
public class HealthyStockEchartDTO {
    private List<String> time;
    private List<Double> data;
}
