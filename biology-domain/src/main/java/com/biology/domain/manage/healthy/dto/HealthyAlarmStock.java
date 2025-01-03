package com.biology.domain.manage.healthy.dto;

import lombok.Data;

@Data
public class HealthyAlarmStock {
    private String time;
    private Integer count;
    private String type;
}
