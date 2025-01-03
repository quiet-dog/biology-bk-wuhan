package com.biology.domain.manage.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnvironmentStock {
    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "数量")
    private Integer count;

    @Schema(description = "时间")
    private String time;
}
