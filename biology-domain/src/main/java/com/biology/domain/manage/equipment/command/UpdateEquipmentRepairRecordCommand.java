package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateEquipmentRepairRecordCommand extends AddEquipmentRepairRecordCommand {

    @Schema(description = "记录`ID")
    private Long recordId;
} 