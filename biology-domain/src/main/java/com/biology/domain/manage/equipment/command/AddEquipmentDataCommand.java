package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddEquipmentDataCommand {

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "阈值传感器ID")
    private Long thresholdId;

    @Schema(description = "监测指标")
    private String monitoringIndicator;

    @Schema(description = "设备数据")
    private double equipmentData;

    @Schema(description = "备注")
    private String remark;
}