package com.biology.domain.manage.nongDuDevice.model;

import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceEntity;
import com.biology.domain.manage.nongDuDevice.command.AddNongDuDeviceCommand;
import com.biology.domain.manage.nongDuDevice.command.UpdateNongDuDeviceCommand;
import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NongDuDeviceModel extends NongDuDeviceEntity {
    private NongDuDeviceService nongDuDeviceService;

    public NongDuDeviceModel(NongDuDeviceService nongDuDeviceService) {
        this.nongDuDeviceService = nongDuDeviceService;
    }

    public NongDuDeviceModel(NongDuDeviceEntity entity, NongDuDeviceService nongDuDeviceService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.nongDuDeviceService = nongDuDeviceService;
    }

    public void loadAddNongDuDeviceCommand(AddNongDuDeviceCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "nongDuDeviceId");
        }
    }

    public void loadUpdateNongDuDeviceCommand(UpdateNongDuDeviceCommand command) {
        if (command != null) {
            loadAddNongDuDeviceCommand(command);
        }
    }
}
