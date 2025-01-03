package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateEquipmentInspectionRecordCommand extends AddEquipmentInspectionRecordCommand {

    @Schema(description = "记录ID")
    private Long recordId;
} 