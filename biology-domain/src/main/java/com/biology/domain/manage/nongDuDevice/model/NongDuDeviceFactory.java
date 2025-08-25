package com.biology.domain.manage.nongDuDevice.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceEntity;
import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceService;
import com.biology.domain.manage.nongDuDevice.model.NongDuDeviceModel;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NongDuDeviceFactory {
    private final NongDuDeviceService nongDuDeviceService;

    public NongDuDeviceModel create() {
        return new NongDuDeviceModel(nongDuDeviceService);
    }

    public NongDuDeviceModel loadById(Long id) {
        NongDuDeviceEntity byId = nongDuDeviceService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new NongDuDeviceModel(byId, nongDuDeviceService);
    }
}
