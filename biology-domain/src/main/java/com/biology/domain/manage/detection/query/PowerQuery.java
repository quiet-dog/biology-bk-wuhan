package com.biology.domain.manage.detection.query;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PowerQuery {
    private String des;

    private String dayType;

    private String type;

    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "区域")
    private String area;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "环境ID")
    private Long environmentId;
}
