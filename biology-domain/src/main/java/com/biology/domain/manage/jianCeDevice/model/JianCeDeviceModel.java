package com.biology.domain.manage.jianCeDevice.model;

import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceEntity;
import com.biology.domain.manage.jianCeDevice.command.AddJianCeDeviceCommand;
import com.biology.domain.manage.jianCeDevice.command.UpdateJianCeDeviceCommand;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class JianCeDeviceModel extends JianCeDeviceEntity {
    private JianCeDeviceService jianCeDeviceService;

    public JianCeDeviceModel(JianCeDeviceService jianCeDeviceService) {
        this.jianCeDeviceService = jianCeDeviceService;
    }

    public JianCeDeviceModel(JianCeDeviceEntity entity, JianCeDeviceService jianCeDeviceService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.jianCeDeviceService = jianCeDeviceService;
    }

    public void loadAddJianCeDeviceCommand(AddJianCeDeviceCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "jianCeDeviceId");
        }
    }

    public void loadUpdateJianCeDeviceCommand(UpdateJianCeDeviceCommand command) {
        if (command != null) {
            loadAddJianCeDeviceCommand(command);
        }
    }
}
