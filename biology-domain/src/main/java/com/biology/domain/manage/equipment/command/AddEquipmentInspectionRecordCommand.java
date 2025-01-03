package com.biology.domain.manage.equipment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;

@Data
public class AddEquipmentInspectionRecordCommand {

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "检修编号")
    private String inspectionCode;

    @Schema(description = "检修日期")
    private Date inspectionDate;

    @Schema(description = "检修内容")
    private String inspectionContent;

    @Schema(description = "检修人员")
    private String inspector;

    @Schema(description = "故障原因")
    private String faultReason;

    @Schema(description = "检修图片路径")
    private JsonNode inspectionImagePath;

    @Schema(description = "检修报告路径")
    private JsonNode inspectionReportPath;
} 
