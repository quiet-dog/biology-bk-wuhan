package com.biology.domain.manage.report.dto;

import java.util.List;

import lombok.Data;

@Data
public class StockEchartDTO {
    private List<String> xData;

    private List<Integer> seriesData;
}
