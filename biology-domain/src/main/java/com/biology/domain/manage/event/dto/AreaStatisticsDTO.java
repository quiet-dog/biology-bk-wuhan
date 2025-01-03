package com.biology.domain.manage.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AreaStatisticsDTO {
    @Schema(description = "区域")
    private String manufacturer;

    private Integer count;
}
