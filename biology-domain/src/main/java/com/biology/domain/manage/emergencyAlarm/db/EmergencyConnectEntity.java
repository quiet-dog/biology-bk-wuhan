package com.biology.domain.manage.emergencyAlarm.db;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_emergency_connect")
@ApiModel(value = "EmergencyConnectEntity对象", description = "关联的报警手册表")
public class EmergencyConnectEntity extends Model<EmergencyConnectEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "emergency_alarm_id")
    private Long emergencyAlarmId;

    @TableField(value = "emergency_id")
    private Long emergencyId;

    @TableField(value = "sop_id")
    private Long sopId;

    @Override
    public Serializable pkVal() {
        return this.emergencyAlarmId;
    }
}
