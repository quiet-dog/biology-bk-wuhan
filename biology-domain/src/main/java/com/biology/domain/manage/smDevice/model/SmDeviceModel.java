package com.biology.domain.manage.smDevice.model;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.smDevice.command.AddSmDeviceCommand;
import com.biology.domain.manage.smDevice.command.UpdateSmDeviceCommand;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.smDevice.db.SmDeviceService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SmDeviceModel extends SmDeviceEntity {
    private SmDeviceService smDeviceService;

    private PersonnelService personnelService;

    public SmDeviceModel(SmDeviceService smDeviceService, PersonnelService pService) {
        this.smDeviceService = smDeviceService;
        this.personnelService = pService;
    }

    public SmDeviceModel(SmDeviceEntity entity, SmDeviceService smDeviceService, PersonnelService pService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.smDeviceService = smDeviceService;
        this.personnelService = pService;
    }

    public void loadAddSmDeviceCommand(AddSmDeviceCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "smDeviceId");
        }
    }

    public void loadUpdateSmDeviceCommand(UpdateSmDeviceCommand command) {
        if (command != null) {
            loadAddSmDeviceCommand(command);
        }
    }

    public boolean insert() {
        if (getPersonnelId() == null && getPersonnelId() == 0) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, getPersonnelId(), "人员");
        }

        PersonnelEntity pEntity = personnelService.getById(getPersonnelId());
        if (pEntity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, getPersonnelId(), "人员");
        }

        return super.insert();
    }

}
