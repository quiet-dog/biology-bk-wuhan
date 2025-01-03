package com.biology.domain.manage.emergencyEvent.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_emergency_event_alarm")
@ApiModel(value = "EmergencyEventAlarmEntity对象", description = "报警事件的报警信息表")
public class EmergencyEventAlarmEntity {
    @TableId(value = "emergency_event_id")
    private Long emergencyEventId;

    @TableField(value = "emergency_alarm_id")
    private Long emergencyAlarmId;
}
