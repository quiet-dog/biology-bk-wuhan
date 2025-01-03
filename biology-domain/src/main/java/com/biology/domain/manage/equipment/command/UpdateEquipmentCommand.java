package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateEquipmentCommand extends AddEquipmentCommand {

    @Schema(description = "设备ID")
    private Long equipmentId;
}
