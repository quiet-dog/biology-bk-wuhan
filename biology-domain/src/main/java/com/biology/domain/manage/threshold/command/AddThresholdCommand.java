package com.biology.domain.manage.threshold.command;

import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddThresholdCommand {

    @Schema(description = "设备Id")
    private Long equipmentId;

    @Schema(description = "传感器名称")
    private String sensorName;

    @Schema(description = "传感器型号")
    private String sensorModel;

    @Schema(description = "设备指标")
    private String equipmentIndex;

    @Schema(description = "指标单位")
    private String unit;

    @Schema(description = "阀值设置")
    private List<AddThresholdValue> values;

    @Schema(description = "应急预案Ids")
    private List<Long> emergencyIds;

    @Schema(description = "SOPIds")
    private List<Long> sopIds;

    private String outId;
}
