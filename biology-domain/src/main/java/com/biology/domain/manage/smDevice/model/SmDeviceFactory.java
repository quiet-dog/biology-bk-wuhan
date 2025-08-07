package com.biology.domain.manage.smDevice.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.smDevice.db.SmDeviceService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SmDeviceFactory {
    private final SmDeviceService smDeviceService;

    private final PersonnelService pService;

    public SmDeviceModel create() {
        return new SmDeviceModel(smDeviceService, pService);
    }

    public SmDeviceModel loadById(Long id) {
        SmDeviceEntity byId = smDeviceService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new SmDeviceModel(byId, smDeviceService, pService);
    }

}
