package com.biology.domain.manage.detection.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DetectionStockQuery {
    @Schema(description = "单位名称")
    private String unitName;

    @Schema(description = "环境描述名称")
    private String description;
}
