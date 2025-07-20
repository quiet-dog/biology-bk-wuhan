package com.biology.domain.manage.detection.dto;

import java.util.List;

import lombok.Data;

@Data
public class SeriesDTO {
    private String name;
    private String type = "line";
    private String stack = "数值";
    private List<Double> data;
}
