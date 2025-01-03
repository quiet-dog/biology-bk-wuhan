package com.biology.domain.manage.detection.dto;

import java.util.List;

import lombok.Data;

@Data
public class DetectionAreaTypeEchartDTO {
    private List<String> types;
    private List<Double> datas;
}
