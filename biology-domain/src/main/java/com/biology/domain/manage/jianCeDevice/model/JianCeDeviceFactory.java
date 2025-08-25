package com.biology.domain.manage.jianCeDevice.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceEntity;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JianCeDeviceFactory {
    private final JianCeDeviceService jianCeDeviceService;

    public JianCeDeviceModel create() {
        return new JianCeDeviceModel(jianCeDeviceService);
    }

    public JianCeDeviceModel loadById(Long id) {
        JianCeDeviceEntity byId = jianCeDeviceService.getById(id);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, id, "接收");
        }
        return new JianCeDeviceModel(byId, jianCeDeviceService);
    }
}
