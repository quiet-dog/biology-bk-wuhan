package com.biology.domain.manage.websocket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EquipmentInfoDTO {

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "阈值传感器ID")
    private Long thresholdId;

    @Schema(description = "传感器名称")
    private String sensorName;

    @Schema(description = "传感器值")
    private double value;

}
