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
@TableName(value = "manage_equipment_inspection_record", autoResultMap = true)
@ApiModel(value = "EquipmentInspectionRecordEntity", description = "设备检修记录")
public class EquipmentInspectionRecordEntity extends BaseEntity<EquipmentInspectionRecordEntity> {

    @ApiModelProperty("记录ID")
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;

    @ApiModelProperty("设备ID")
    @TableField("equipment_id")
    private Long equipmentId;

    @ApiModelProperty("检修编号")
    @TableField("inspection_code")
    private String inspectionCode;

    @ApiModelProperty("检修日期")
    @TableField("inspection_date")
    private Date inspectionDate;

    @ApiModelProperty("检修内容")
    @TableField("inspection_content")
    private String inspectionContent;

    @ApiModelProperty("检修人员")
    @TableField("inspector")
    private String inspector;

    @ApiModelProperty("故障原因")
    @TableField("fault_reason")
    private String faultReason;

    @ApiModelProperty("检修图片路径")
    @TableField(value = "inspection_image_path", typeHandler = JacksonTypeHandler.class)
    private JsonNode inspectionImagePath;

    @ApiModelProperty("检修报告路径")
    @TableField(value = "inspection_report_path", typeHandler = JacksonTypeHandler.class)
    private JsonNode inspectionReportPath;

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