package com.biology.domain.manage.equipment.command;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddEquipmentInspectionManualCommand {

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "手册版本")
    private String manualVersion;

    @Schema(description = "适用范围")
    private String suitableScope;

    @Schema(description = "手册编号")
    private String manualCode;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "检修手册附件路径")
    private JsonNode manualFilePath;
}
