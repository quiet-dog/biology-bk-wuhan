package com.biology.domain.manage.xwAlarm.model;

import com.biology.domain.manage.xwAlarm.command.AddXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.command.UpdateXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.db.XwAlarmEntity;
import com.biology.domain.manage.xwAlarm.db.XwAlarmService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class XwAlarmModel extends XwAlarmEntity {
    private XwAlarmService xwAlarmService;

    public XwAlarmModel(XwAlarmService xwAlarmService) {
        this.xwAlarmService = xwAlarmService;
    }

    public XwAlarmModel(XwAlarmEntity entity, XwAlarmService xwAlarmService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.xwAlarmService = xwAlarmService;
    }

    public void loadAddXwAlarmCommand(AddXwAlarmCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "xwAlarmId");
        }
    }

    public void loadUpdateXwAlarmCommand(UpdateXwAlarmCommand command) {
        if (command != null) {
            loadAddXwAlarmCommand(command);
        }
    }

}
