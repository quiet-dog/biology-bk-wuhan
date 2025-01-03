package com.biology.domain.manage.threshold.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddThresholdEmergencyCommand {
    @Schema(description = "阈值Id")
    private Long thresholdId;

    @Schema(description = "应急预案Id")
    private Long emergencyId;
}
