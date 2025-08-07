package com.biology.domain.manage.smDevice.command;

import lombok.Data;

@Data
public class UpdateSmDeviceCommand extends AddSmDeviceCommand {
    private Long smDeviceId;
}
