package com.biology.domain.manage.xsDevice.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;
import com.biology.domain.manage.xsDevice.db.XsDeviceService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class XsDeviceFactory {
    private final XsDeviceService xsDeviceService;

    public XsDeviceModel create() {
        return new XsDeviceModel(xsDeviceService);
    }

    public XsDeviceModel loadById(Long id) {
        XsDeviceEntity byId = xsDeviceService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new XsDeviceModel(byId, xsDeviceService);
    }
}
