package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;

@Data
public class AddEquipmentRepairRecordCommand {

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "维修编号")
    @Getter
    @Setter
    private String repairCode;

    @Schema(description = "维修日期")
    private Date repairDate;

    @Schema(description = "维修内容")
    private String repairContent;

    @Schema(description = "故障原因")
    private String faultReason;

    @Schema(description = "维修人员")
    private String repairPersonnel;

    @Schema(description = "维修费用")
    private String repairCost;

    @Schema(description = "维修结果")
    private String repairResult;

    @Schema(description = "维修图片路径")
    private JsonNode repairImagePath;

}
