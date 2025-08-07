package com.biology.domain.manage.xwDevice.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;
import com.biology.domain.manage.xwDevice.db.XwDeviceService;
import com.biology.domain.manage.xwDevice.model.XwDeviceModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XwDeviceFactory {
    private final XwDeviceService xwDeviceService;

    public XwDeviceModel create() {
        return new XwDeviceModel(xwDeviceService);
    }

    public XwDeviceModel loadById(Long id) {
        XwDeviceEntity byId = xwDeviceService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new XwDeviceModel(byId, xwDeviceService);
    }
}
