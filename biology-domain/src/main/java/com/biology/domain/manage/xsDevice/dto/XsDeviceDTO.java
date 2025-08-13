package com.biology.domain.manage.xsDevice.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;

import lombok.Data;

@Data
public class XsDeviceDTO {
    private Long xsDeviceId;

    private String deviceSn;

    private String name;

    private String area;

    private Long lastTime;

    public XsDeviceDTO(XsDeviceEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
