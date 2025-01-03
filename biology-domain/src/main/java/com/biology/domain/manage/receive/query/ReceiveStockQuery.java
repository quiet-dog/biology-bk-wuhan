package com.biology.domain.manage.receive.query;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReceiveStockQuery {
    @Schema(description = "物料id")
    private Long materialsId;

    @Schema(description = "物料名称")
    private String materialsName;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;
}
