package com.biology.domain.manage.kongTiaoDevice.model;

import com.biology.domain.manage.kongTiaoDevice.command.AddKongTiaoDeviceCommand;
import com.biology.domain.manage.kongTiaoDevice.command.UpdateKongTiaoDeviceCommand;
import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceEntity;
import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class KongTiaoDeviceModel extends KongTiaoDeviceEntity {
    private KongTiaoDeviceService kongTiaoDeviceService;

    public KongTiaoDeviceModel(KongTiaoDeviceService kongTiaoDeviceService) {
        this.kongTiaoDeviceService = kongTiaoDeviceService;
    }

    public KongTiaoDeviceModel(KongTiaoDeviceEntity entity, KongTiaoDeviceService kongTiaoDeviceService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.kongTiaoDeviceService = kongTiaoDeviceService;
    }

    public void loadAddKongTiaoDeviceCommand(AddKongTiaoDeviceCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "kongTiaoDeviceId");
        }
    }

    public void loadUpdateKongTiaoDeviceCommand(UpdateKongTiaoDeviceCommand command) {
        if (command != null) {
            loadAddKongTiaoDeviceCommand(command);
        }
    }
}
