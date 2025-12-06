package com.biology.domain.manage.kongTiaoDevice.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.kongTiaoData.dto.KongTiaoDataDTO;
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

    private String deviceType;

    private Date createTime;

    private String isOnlineStr;

    public KongTiaoDeviceDTO(KongTiaoDeviceEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }

    public void addOther() {
        KongTiaoDataDTO kongTiaoDataDTO = CacheCenter.kongTiaoDataCache.getObjectById(getDeviceSn());
        if (kongTiaoDataDTO != null) {
            setIsOnlineStr(kongTiaoDataDTO.getIsOnline() ? "在线" : "离线");
        } else {
            setIsOnlineStr("离线");
        }
    }
}
