package com.biology.domain.manage.smData.query;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SmDataLuanShengQuiery {
    @Schema(description = "点位类型")
    private List<String> dianWeis;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "设备ID")
    private Long smDeviceId;
}
