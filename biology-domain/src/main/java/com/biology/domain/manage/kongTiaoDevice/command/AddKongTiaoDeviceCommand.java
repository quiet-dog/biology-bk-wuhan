package com.biology.domain.manage.kongTiaoDevice.command;

import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceLabel;

import lombok.Data;

@Data
public class AddKongTiaoDeviceCommand {

    private String deviceSn;

    private String name;

    private String area;

    private KongTiaoDeviceLabel extend;

}
