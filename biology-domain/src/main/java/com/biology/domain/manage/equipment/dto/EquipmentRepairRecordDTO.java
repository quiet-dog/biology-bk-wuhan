package com.biology.domain.manage.equipment.dto;

import com.biology.domain.manage.equipment.db.EquipmentRepairRecordEntity;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EquipmentRepairRecordDTO {

    @Schema(description = "记录ID")
    private Long recordId;

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "设备")
    private EquipmentDTO equipment;

    private String equipmentName;

    @Schema(description = "维修编号")
    private String repairCode;

    @Schema(description = "维修内容")
    private String repairContent;

    @Schema(description = "维修日期")
    private Date repairDate;

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

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "是否删除")
    private Boolean deleted;

    public EquipmentRepairRecordDTO(EquipmentRepairRecordEntity entity) {
        BeanUtils.copyProperties(entity, this);
        if (entity != null && equipment != null) {
            setEquipmentName(equipment.getEquipmentName());
        }
    }
}
