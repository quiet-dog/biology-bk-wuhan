package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateEquipmentDailyInspectionRecordCommand extends AddEquipmentDailyInspectionRecordCommand {

    @Schema(description = "记录ID")
    private Long recordId;
}