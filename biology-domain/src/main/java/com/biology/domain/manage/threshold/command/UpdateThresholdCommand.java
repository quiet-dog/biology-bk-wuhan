package com.biology.domain.manage.threshold.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class UpdateThresholdCommand extends AddThresholdCommand {
    @NotNull
    @Positive
    private Long thresholdId;
}
