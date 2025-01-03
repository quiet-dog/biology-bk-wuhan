package com.biology.domain.manage.emergencyAlarm.command;

import lombok.Data;

@Data
public class UpdateEmergencyAlarmCommand extends AddEmergencyAlarmCommand {
    private Long emergencyAlarmId;
}
