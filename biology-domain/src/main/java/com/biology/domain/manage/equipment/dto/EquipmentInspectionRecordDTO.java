package com.biology.domain.manage.equipment.dto;

import com.biology.domain.manage.equipment.db.EquipmentInspectionRecordEntity;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class EquipmentInspectionRecordDTO {

    @Schema(description = "记录ID")
    private Long recordId;

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "设备")
    private EquipmentDTO equipment;

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

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "是否删除")
    private Boolean deleted;

    public EquipmentInspectionRecordDTO(EquipmentInspectionRecordEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
} 