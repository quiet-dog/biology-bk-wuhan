package com.biology.domain.manage.materials.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class StockEchatDTO {
    private List<String> xAxisData;

    private List<Double> seriesData;
}
