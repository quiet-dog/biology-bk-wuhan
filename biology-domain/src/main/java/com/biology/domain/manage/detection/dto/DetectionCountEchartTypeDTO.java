package com.biology.domain.manage.detection.dto;

import java.util.List;

import lombok.Data;

@Data
public class DetectionCountEchartTypeDTO {
    private List<String> time;

    private List<Double> data;
}