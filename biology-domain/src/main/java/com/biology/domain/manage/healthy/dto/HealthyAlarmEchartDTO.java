package com.biology.domain.manage.healthy.dto;

import java.util.List;

import lombok.Data;

@Data
public class HealthyAlarmEchartDTO {
    private String type;

    private List<Integer> data;

    private List<String> time;
}
