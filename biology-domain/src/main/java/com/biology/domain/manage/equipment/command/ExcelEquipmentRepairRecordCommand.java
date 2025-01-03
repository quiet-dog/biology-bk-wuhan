package com.biology.domain.manage.equipment.command;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import com.biology.common.annotation.ExcelColumn;

import java.util.Date;

@Data
public class ExcelEquipmentRepairRecordCommand {

    @Schema(description = "设备编号")
    @ExcelColumn(name = "设备编号")
    private String equipmentCode;

    @Schema(description = "维修编号")
    @ExcelColumn(name = "维修编号")
    private String repairCode;

    @Schema(description = "维修日期")
    @ExcelColumn(name = "维修日期")
    private Date repairDate;

    @Schema(description = "维修内容")
    @ExcelColumn(name = "维修内容")
    private String repairContent;

    @Schema(description = "故障原因")
    @ExcelColumn(name = "故障原因")
    private String faultReason;

    @Schema(description = "维修人员")
    @ExcelColumn(name = "维修人员")
    private String repairPersonnel;

    @Schema(description = "维修费用")
    @ExcelColumn(name = "维修费用")
    private String repairCost;

    @Schema(description = "维修结果")
    @ExcelColumn(name = "维修结果")
    private String repairResult;
}