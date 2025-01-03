package com.biology.domain.manage.emergencyEvent.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_emergency_event_personnel")
@ApiModel(value = "EmergencyEventAlarmEntity对象", description = "报警事件的处理人表")
public class EmergencyUserEntity extends Model<EmergencyUserEntity> {

    @TableId(value = "emergency_event_id")
    private Long emergencyEventId;

    @TableField(value = "personnel_id")
    private Long personnelId;
}
