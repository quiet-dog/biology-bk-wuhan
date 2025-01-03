package com.biology.domain.manage.equipment.command;

import com.biology.common.annotation.ExcelColumn;

// import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ExcelEquipmentMaintenanceManualCommand {
    @Schema(description = "设备编号")
    @ExcelColumn(name = "设备编号")
    private String equipmentCode;

    @Schema(description = "手册版本")
    @ExcelColumn(name = "手册版本")
    private String manualVersion;
}
