package com.biology.domain.manage.emergencyEvent.command;

import lombok.Data;

@Data
public class UpdateEmergencyEventCommand extends AddEmergencyEventCommand {
    public Long emergencyEventId;
}
