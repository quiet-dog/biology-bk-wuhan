package com.biology.domain.manage.xwAlarm.command;

import lombok.Data;

@Data
public class UpdateXwAlarmCommand extends AddXwAlarmCommand {
    private Long xwAlarmId;
}
