package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateEquipmentInspectionManualCommand extends AddEquipmentInspectionManualCommand {

    @Schema(description = "手册ID")
    private Long manualId;
}
