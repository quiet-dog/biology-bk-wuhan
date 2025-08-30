package com.biology.domain.manage.kongTiaoDevice.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceEntity;
import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KongTiaoDeviceFactory {

    private final KongTiaoDeviceService kongTiaoDeviceService;

    public KongTiaoDeviceModel create() {
        return new KongTiaoDeviceModel(kongTiaoDeviceService);
    }

    public KongTiaoDeviceModel loadById(Long id) {
        KongTiaoDeviceEntity byId = kongTiaoDeviceService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new KongTiaoDeviceModel(byId, kongTiaoDeviceService);
    }
}
