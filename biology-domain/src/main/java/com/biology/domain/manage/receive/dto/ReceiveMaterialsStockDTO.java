package com.biology.domain.manage.receive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ReceiveMaterialsStockDTO {

    @Schema(description = "物料类型")
    private String materialsType;

    @Schema(description = "物料数量")
    private double count;

    @Schema(description = "物料名称")
    private String name;

    @Schema(description = "物料单位")
    private String unit;

    @Schema(description = "物料时间")
    private String dataTime;

    @Schema(description = "物料领用说明")
    private String receiveExplain;
}
