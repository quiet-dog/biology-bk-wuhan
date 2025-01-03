package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;

@Data
public class AddEquipmentDailyInspectionRecordCommand {

    @Schema(description = "巡检日期")
    private Date inspectionDate;

    @Schema(description = "巡检编号")
    @Getter
    @Setter
    private String inspectionCode;

    @Schema(description = "任务描述")
    private String taskDescription;

    @Schema(description = "巡检人员")
    private String inspector;

    @Schema(description = "异常数")
    private Integer anomalyCount;

    @Schema(description = "异常说明")
    private String anomalyDescription;

    @Schema(description = "巡检图片路径")
    private JsonNode inspectionImagePath;
}
