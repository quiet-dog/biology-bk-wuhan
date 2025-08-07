package com.biology.domain.manage.xwDevice.command;

import lombok.Data;

@Data
public class AddXwDeviceCommand {

    private String cameraId;

    private String name;

    private String seatNumber;

    private String content;
}
