package com.biology.domain.manage.shiJuan.dto;

import java.util.List;

import lombok.Data;

@Data
public class CePingJieGuoTongJiEchartResult {
    private List<String> legend;
    private List<String> xAxis;
    private List<CePingJieGuoTongJiEchart> series;

    public CePingJieGuoTongJiEchartResult(List<String> legend, List<String> xAxis,
            List<CePingJieGuoTongJiEchart> series) {
        this.legend = legend;
        this.xAxis = xAxis;
        this.series = series;
    }

}
