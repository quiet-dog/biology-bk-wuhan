package com.biology.domain.manage.detection.dto;

import java.util.List;

import lombok.Data;

@Data
public class SeriesDTO {
    private String name;
    private String type;
    private String stack;
    private List<Double> data;
}
