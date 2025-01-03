package com.biology.domain.manage.equipment.command;

import com.biology.common.annotation.ExcelColumn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// import com.fasterxml.jackson.databind.JsonNode;

@Data
public class ExcelEquipmentInspectionManualCommand {
    @Schema(description = "设备名称")
    @ExcelColumn(name = "设备名称")
    private String equipmentName;

    @Schema(description = "设备ID")
    @ExcelColumn(name = "设备ID")
    private Long equipmentId;

    @Schema(description = "手册版本")
    @ExcelColumn(name = "手册版本")
    private String manualVersion;

    @Schema(description = "适用范围")
    @ExcelColumn(name = "适用范围")
    private String suitableScope;

    @Schema(description = "设备编号")
    @ExcelColumn(name = "设备编号")
    private String equipmentCode;

    // @Schema(description = "手册编号")
    // @ExcelColumn(name = "手册编号")
    // private String manualCode;

    // @Schema(description = "是否启用")
    // @ExcelColumn(name = "是否启用")
    // private Boolean isEnabled;

    // @Schema(description = "检修手册附件路径")
    // private JsonNode manualFilePath;
}
