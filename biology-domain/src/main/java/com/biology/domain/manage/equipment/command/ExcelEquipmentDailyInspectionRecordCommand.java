package com.biology.domain.manage.equipment.command;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import com.biology.common.annotation.ExcelColumn;

import java.util.Date;

@Data
public class ExcelEquipmentDailyInspectionRecordCommand {
    @Schema(description = "巡检日期")
    @ExcelColumn(name = "巡检日期")
    private Date inspectionDate;

    @Schema(description = "巡检编号")
    @ExcelColumn(name = "巡检编号")
    private String inspectionCode;

    @Schema(description = "任务描述")
    @ExcelColumn(name = "任务描述")
    private String taskDescription;

    @Schema(description = "巡检人员")
    @ExcelColumn(name = "巡检人员")
    private String inspector;

    @Schema(description = "异常数")
    @ExcelColumn(name = "异常数")
    private Integer anomalyCount;

    @Schema(description = "异常说明")
    @ExcelColumn(name = "异常说明")
    private String anomalyDescription;
}