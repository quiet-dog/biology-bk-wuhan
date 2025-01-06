package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.common.core.base.BaseEntity;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName(value = "manage_equipment_daily_inspection_record", autoResultMap = true)
@ApiModel(value = "EquipmentDailyInspectionRecordEntity", description = "巡检记录")
public class EquipmentDailyInspectionRecordEntity extends BaseEntity<EquipmentDailyInspectionRecordEntity> {

    @ApiModelProperty("记录ID")
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;

    @ApiModelProperty("巡检日期")
    @TableField("inspection_date")
    private Date inspectionDate;

    @ApiModelProperty("巡检编号")
    @TableField("inspection_code")
    private String inspectionCode;

    @ApiModelProperty("任务描述")
    @TableField("task_description")
    private String taskDescription;

    @ApiModelProperty("巡检人员")
    @TableField("inspector")
    private String inspector;

    @ApiModelProperty("巡检人员ID")
    @TableField("inspector_id")
    private Long inspectorId;

    @ApiModelProperty("异常数")
    @TableField("anomaly_count")
    private Integer anomalyCount;

    @ApiModelProperty("异常描述")
    @TableField("anomaly_description")
    private String anomalyDescription;

    @ApiModelProperty("巡检结果")
    @TableField("inspection_result")
    private String inspectionResult;

    @ApiModelProperty("巡检图片路径")
    @TableField(value = "inspection_image_path", typeHandler = JacksonTypeHandler.class)
    private JsonNode inspectionImagePath;

    // @ApiModelProperty("创建时间")
    // @TableField("create_time")
    // private Date createTime;

    // @ApiModelProperty("更新时间")
    // @TableField("update_time")
    // private Date updateTime;

    // @ApiModelProperty("删除标志")
    // @TableField("deleted")
    // private Boolean deleted;
}