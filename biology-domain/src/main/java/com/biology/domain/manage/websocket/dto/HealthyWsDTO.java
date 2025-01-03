package com.biology.domain.manage.websocket.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HealthyWsDTO {
    @Schema(description = "人员ID")
    private Long personnelId;

    @Schema(description = "体温")
    private double temperature;

    @ApiModelProperty("心率")
    @Schema(description = "心率")
    private double heartRate;

    @ApiModelProperty("收缩压")
    @Schema(description = "收缩压")
    private double highBloodPressure;

    @ApiModelProperty("舒张压")
    @Schema(description = "舒张压")
    private double lowBloodPressure;
}
