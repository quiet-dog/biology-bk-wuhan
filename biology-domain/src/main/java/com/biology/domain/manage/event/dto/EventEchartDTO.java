package com.biology.domain.manage.event.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class EventEchartDTO {
    private String type;

    private String eventDate;

    private List<String> times;

    private List<Double> data;
}