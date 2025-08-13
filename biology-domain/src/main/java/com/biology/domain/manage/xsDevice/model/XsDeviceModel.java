package com.biology.domain.manage.xsDevice.model;

import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.xsDevice.command.AddXsDeviceCommand;
import com.biology.domain.manage.xsDevice.command.UpdateXsDeviceCommand;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;
import com.biology.domain.manage.xsDevice.db.XsDeviceService;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class XsDeviceModel extends XsDeviceEntity {

    private XsDeviceService xsDeviceService;

    public XsDeviceModel(XsDeviceService xsDeviceService) {
        this.xsDeviceService = xsDeviceService;
    }

    public XsDeviceModel(XsDeviceEntity entity, XsDeviceService xsDeviceService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.xsDeviceService = xsDeviceService;
    }

    public void loadAddXsDeviceCommand(AddXsDeviceCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "xsDeviceId");
        }
    }

    public void loadUpdateXsDeviceCommand(UpdateXsDeviceCommand command) {
        if (command != null) {
            loadAddXsDeviceCommand(command);
        }
    }
}
