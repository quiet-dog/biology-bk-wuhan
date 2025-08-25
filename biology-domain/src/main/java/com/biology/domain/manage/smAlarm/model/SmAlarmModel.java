package com.biology.domain.manage.smAlarm.model;

import com.biology.domain.manage.smAlarm.command.AddSmAlarmCommand;
import com.biology.domain.manage.smAlarm.command.UpdateSmAlarmCommand;
import com.biology.domain.manage.smAlarm.db.SmAlarmEntity;
import com.biology.domain.manage.smAlarm.db.SmAlarmService;
import com.biology.domain.manage.smAlarm.db.SmAlarmEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SmAlarmModel extends SmAlarmEntity {
    private SmAlarmService smAlarmService;

    public SmAlarmModel(SmAlarmService smAlarmService) {
        this.smAlarmService = smAlarmService;
    }

    public SmAlarmModel(SmAlarmEntity entity, SmAlarmService smAlarmService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.smAlarmService = smAlarmService;
    }

    public void loadAddSmAlarmCommand(AddSmAlarmCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "smAlarmId");
        }
    }

    public void loadUpdateSmAlarmCommand(UpdateSmAlarmCommand command) {
        if (command != null) {
            loadAddSmAlarmCommand(command);
        }
    }
}
