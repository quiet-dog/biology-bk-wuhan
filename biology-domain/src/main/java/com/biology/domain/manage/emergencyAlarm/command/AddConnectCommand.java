package com.biology.domain.manage.emergencyAlarm.command;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

@Data
public class AddConnectCommand {
    private Long emergencyAlarmId;

    private Long emergencyId;

    private Long sopId;

}
