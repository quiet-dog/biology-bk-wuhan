package com.biology.domain.manage.report.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class StockReportQuery {
    @Schema(description = "类型")
    private String dayType;
    // @Schema(description = "物料名称")
    // private String marterialsName;
}
