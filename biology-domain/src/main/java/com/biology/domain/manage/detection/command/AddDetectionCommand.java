package com.biology.domain.manage.detection.command;

import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddDetectionCommand {

    @Schema(description = "环境ID")
    private Long environmentId;

    @Schema(description = "数值")
    private double value;

    @Schema(description = "功耗")
    private double power;

    @Schema(description = "水用")
    private double waterValue;

    @Schema(description = "用电量")
    private double electricityValue;
}
