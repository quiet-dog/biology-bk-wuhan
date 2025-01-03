package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateEquipmentMaintenanceManualCommand extends AddEquipmentMaintenanceManualCommand {

    @Schema(description = "维修手册ID")
    private Long manualId;
} 