package com.biology.domain.manage.xsData.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class XsDataLuanShengQuery {
    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "设备ID")
    private Long xsDeviceId;
}
