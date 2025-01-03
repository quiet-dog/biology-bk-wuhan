package com.biology.domain.manage.event.dto;

import lombok.Data;

@Data
public class WeekStatisticsDTO {
    private String type;

    private String dataTime;

    private double count;
}
