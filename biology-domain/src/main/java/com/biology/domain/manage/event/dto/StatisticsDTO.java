package com.biology.domain.manage.event.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class StatisticsDTO {

    private Date date;

    private List<WeekStatisticsDTO> data;

}
