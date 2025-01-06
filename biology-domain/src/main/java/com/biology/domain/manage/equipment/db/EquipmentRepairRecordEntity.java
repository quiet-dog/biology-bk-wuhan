package com.biology.domain.manage.equipment.db;

import java.math.BigDecimal;
import java.util.Date;

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

@Getter
@Setter
@TableName(value = "manage_equipment_repair_record", autoResultMap = true)
@ApiModel(value = "EquipmentRepairRecordEntity", description = "设备维修记录")
public class EquipmentRepairRecordEntity extends BaseEntity<EquipmentRepairRecordEntity> {

    @ApiModelProperty("记录ID")
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;

    @ApiModelProperty("设备ID")
    @TableField("equipment_id")
    private Long equipmentId;

    @ApiModelProperty("维修编号")
    @TableField("repair_code")
    private String repairCode;

    @ApiModelProperty("维修日期")
    @TableField("repair_date")
    private Date repairDate;

    @ApiModelProperty("故障原因")
    @TableField("fault_reason")
    private String faultReason;

    @ApiModelProperty("维修人员")
    @TableField("repair_personnel")
    private String repairPersonnel;

    @ApiModelProperty("维修内容")
    @TableField("repair_content")
    private String repairContent;

    @ApiModelProperty("维修费用")
    @TableField("repair_cost")
    private String repairCost;

    @ApiModelProperty("维修结果")
    @TableField("repair_result")
    private String repairResult;

    @ApiModelProperty("维修图片路径")
    @TableField(value = "repair_image_path", typeHandler = JacksonTypeHandler.class)
    private JsonNode repairImagePath;

    // @ApiModelProperty("创建时间")
    // @TableField("create_time")
    // private Date createTime;

    // @ApiModelProperty("更新时间")
    // @TableField("update_time")
    // private Date updateTime;

    // @ApiModelProperty("删除标志")
    // @TableField("deleted")
    // private Boolean deleted;

    @Override
    public Long pkVal() {
        return this.recordId;
    }
}