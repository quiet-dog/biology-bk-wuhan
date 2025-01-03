package com.biology.domain.manage.threshold.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddThresholdValue {
    @Schema(description = "报警级别")
    private String level;

    @Schema(description = "最小值")
    private double min;

    @Schema(description = "最大值")
    private double max;

}
