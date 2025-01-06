package com.biology.domain.manage.equipment.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.equipment.db.EquipmentDailyInspectionRecordEntity;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;

@Data
public class EquipmentDailyInspectionRecordDTO {

    @Schema(description = "记录ID")
    private Long recordId;

    @Schema(description = "巡检日期")
    private Date inspectionDate;

    @Schema(description = "巡检编号")
    private String inspectionCode;

    @Schema(description = "任务描述")
    private String taskDescription;

    @Schema(description = "巡检人员")
    private String inspector;

    @Schema(description = "巡检结果")
    private String inspectionResult;

    @Schema(description = "异常数")
    private Integer anomalyCount;

    @Schema(description = "异常说明")
    private String anomalyDescription;

    @Schema(description = "巡检图片路径")
    private JsonNode inspectionImagePath;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "是否删除")
    private Boolean deleted;

    public EquipmentDailyInspectionRecordDTO(EquipmentDailyInspectionRecordEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }
}