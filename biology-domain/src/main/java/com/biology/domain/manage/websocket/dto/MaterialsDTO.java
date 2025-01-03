package com.biology.domain.manage.websocket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MaterialsDTO {
    @Schema(description = "物料id")
    private Long materialsId;

    @Schema(description = "记录类型")
    private String recordType;

    @Schema(description = "物料数值")
    private double value;
}
