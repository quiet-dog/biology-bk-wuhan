package com.biology.domain.manage.alarmlevel.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmDetail {
    @Schema(description = "报警级别")
    private String level;

    @Schema(description = "最小值")
    private double min;

    @Schema(description = "最大值")
    private double max;

    @Schema(description = "环境档案Id")
    private Long environmentId;
}
