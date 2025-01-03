package com.biology.domain.manage.equipment.command;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import com.biology.common.annotation.ExcelColumn;

import java.util.Date;

@Data
public class ExcelEquipmentInspectionRecordCommand {

    @Schema(description = "设备编号")
    @ExcelColumn(name = "设备编号")
    private String equipmentCode;

    @Schema(description = "检修编号")
    @ExcelColumn(name = "检修编号")
    private String inspectionCode;

    @Schema(description = "检修日期")
    @ExcelColumn(name = "检修日期")
    private Date inspectionDate;

    @Schema(description = "检修内容")
    @ExcelColumn(name = "检修内容")
    private String inspectionContent;

    @Schema(description = "检修人员")
    @ExcelColumn(name = "检修人员")
    private String inspector;

    @Schema(description = "故障原因")
    @ExcelColumn(name = "故障原因")
    private String faultReason;
}