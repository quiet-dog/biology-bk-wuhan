package com.biology.domain.manage.xwDevice.model;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.xwDevice.command.AddXwDeviceCommand;
import com.biology.domain.manage.xwDevice.command.UpdateXwDeviceCommand;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;
import com.biology.domain.manage.xwDevice.db.XwDeviceService;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class XwDeviceModel extends XwDeviceEntity {
    private XwDeviceService xwDeviceService;

    public XwDeviceModel(XwDeviceService xwDeviceService) {
        this.xwDeviceService = xwDeviceService;
    }

    public XwDeviceModel(XwDeviceEntity entity, XwDeviceService xwDeviceService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.xwDeviceService = xwDeviceService;
    }

    public void loadAddXwDeviceCommand(AddXwDeviceCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "xwDeviceId");
        }
    }

    public void loadUpdateXwDeviceCommand(UpdateXwDeviceCommand command) {
        if (command != null) {
            loadAddXwDeviceCommand(command);
        }
    }

}
