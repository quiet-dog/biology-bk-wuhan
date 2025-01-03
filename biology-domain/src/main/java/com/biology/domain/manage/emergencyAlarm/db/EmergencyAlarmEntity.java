package com.biology.domain.manage.emergencyAlarm.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_emergency_alarm")
@ApiModel(value = "EmergencyAlarmEntity对象", description = "应急预案报警信息表")
public class EmergencyAlarmEntity extends BaseEntity<EmergencyAlarmEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "emergency_alarm_id", type = IdType.AUTO)
    private Long emergencyAlarmId;

    // @TableField(value = "emergency_id")
    // private Long emergencyId;

    @TableField(value = "environment_id")
    private Long environmentId;

    @TableField(value = "equipment_data_id")
    private Long equipmentDataId;

    @TableField(value = "detection_id")
    private Long detectionId;

    @ApiModelProperty("应急编号")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty("报警类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("报警级别")
    @TableField("level")
    private String level;

    @ApiModelProperty("报警描述")
    @TableField("description")
    private String description;

    @Override
    public Long pkVal() {
        return this.emergencyAlarmId;
    }
}
