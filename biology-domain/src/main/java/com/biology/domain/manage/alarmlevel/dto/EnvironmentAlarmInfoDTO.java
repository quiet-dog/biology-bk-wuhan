package com.biology.domain.manage.alarmlevel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnvironmentAlarmInfoDTO {

    @Schema(description = "设备ID")
    private Long environmentId;

    @Schema(description = "数据")
    private double value;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "功耗")
    private double power;

    @Schema(description = "用水量")
    private double waterValue;

    @Schema(description = "用电量")
    private double electricityValue;
}
