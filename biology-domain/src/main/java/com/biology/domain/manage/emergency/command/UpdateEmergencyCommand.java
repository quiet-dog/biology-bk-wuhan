package com.biology.domain.manage.emergency.command;

import lombok.Data;

@Data
public class UpdateEmergencyCommand extends AddEmergencyCommand {
    private Long emergencyId;
}
