package com.biology.domain.manage.kongTiaoDevice.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceEntity;
import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceLabel;

import lombok.Data;

@Data
public class KongTiaoDeviceDTO {

    private Long kongTiaoDeviceId;

    private String deviceSn;

    private String name;

    private String area;

    private KongTiaoDeviceLabel extend;

    public KongTiaoDeviceDTO(KongTiaoDeviceEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
