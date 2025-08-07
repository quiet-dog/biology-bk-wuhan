package com.biology.domain.manage.xwDevice.command;

import lombok.Data;

@Data
public class UpdateXwDeviceCommand extends AddXwDeviceCommand {
    private Long xwDeviceId;
}
