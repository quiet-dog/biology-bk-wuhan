package com.biology.domain.manage.detection.command;

import lombok.Data;

@Data
public class UpdateDetectionCommand extends AddDetectionCommand {
    private Long detectionId;
}
