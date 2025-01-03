package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.databind.JsonNode;

@Data
public class AddEquipmentMaintenanceManualCommand {

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "手册版本")
    private String manualVersion;

    @Getter
    @Setter
    @Schema(description = "手册编号")
    private String manualCode;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "维修手册附件路径")
    private JsonNode manualFilePath;
} 
