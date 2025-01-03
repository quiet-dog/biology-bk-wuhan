package com.biology.domain.manage.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EventTotalDTO {
    @Schema(description = "今日总数")
    private Integer todayTotal;

    @Schema(description = "全部总数")
    private Integer allTotal;
}
