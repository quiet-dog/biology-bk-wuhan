package com.biology.domain.manage.event.dto;

import java.util.List;

import lombok.Data;

@Data
public class EventEchartOneDTO {
    private String type;

    private String eventDate;

    private List<String> times;

    private List<Integer> data;
}
