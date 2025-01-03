package com.biology.domain.manage.event.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_event")
@ApiModel(value = "EventEntity对象", description = "报警事件表")
public class EventEntity extends BaseEntity<EventEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("事件ID")
    @TableId(value = "event_id", type = IdType.AUTO)
    private Long eventId;

    @ApiModelProperty("报警类型")
    @TableField(value = "type")
    private String type;

    @ApiModelProperty("设备Id")
    @TableField(value = "equipment_id")
    private Long equipmentId;

    @ApiModelProperty("设备值")
    @TableField(value = "equipment_value")
    private double equipmentValue;

    @ApiModelProperty("设备阈值Id")
    @TableField(value = "threshold_id")
    private Long thresholdId;

    @ApiModelProperty("设备单位")
    @TableField(value = "equipment_unit")
    private String equipmentUnit;

    @ApiModelProperty("物料Id")
    @TableField(value = "materials_id")
    private Long materialsId;

    @ApiModelProperty("物料值")
    @TableField(value = "materials_value")
    private double materialsValue;

    @ApiModelProperty("设备单位")
    @TableField(value = "materials_unit")
    private String materialsUnit;

    @ApiModelProperty("环境档案Id")
    @TableField(value = "environment_id")
    private Long environmentId;

    @ApiModelProperty("环境值")
    @TableField(value = "environment_value")
    private double environmentValue;

    @ApiModelProperty("环境报警级别Id")
    @TableField(value = "alarmlevel_id")
    private Long alarmlevelId;

    @ApiModelProperty("环境单位")
    @TableField(value = "environment_unit")
    private String environmentUnit;

    @ApiModelProperty("工艺节点Id")
    @TableField(value = "craft_node_id")
    private Long craftNodeId;

    // @ApiModelProperty("物料阈值Id")
    // @TableField(value = "materials_value_id")
    // private Long materialsValueId;

    @ApiModelProperty("报警级别")
    @TableField(value = "level")
    private String level;

    @ApiModelProperty("报警描述")
    @TableField(value = "description")
    private String description;

    @ApiModelProperty("处理人Id")
    @TableField(value = "handlerId")
    private Long handlerId;

    @Override
    public Serializable pkVal() {
        return this.eventId;
    }
}
