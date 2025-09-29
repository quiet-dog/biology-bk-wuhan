package com.biology.domain.manage.websocket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OnlineDTO {
    @Schema(description = "是否在线")
    private Boolean isOnline;

    @Schema(description = "是否异常")
    private Boolean isException;

    @Schema(description = "设备id")
    private Long equipmentId;

    @Schema(description = "传感器Id")
    private Long thresholdId;

    @Schema(description = "环境档案Id")
    private Long environmentId;

    @Schema(description = "传感器数据")
    private double thresholdData;

    @Schema(description = "环境数据")
    private double environmentData;

    private Long time;
}
