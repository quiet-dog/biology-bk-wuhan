package com.biology.domain.manage.threshold.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OnlineThresholdEchart {
    @Schema(description = "在线的数量")
    private long onlineCount;
    @Schema(description = "离线的数量")
    private long offlineCount;
    @Schema(description = "异常的数量")
    private long exceptionCount;

    @Schema(description = "总数")
    private long total;
}
