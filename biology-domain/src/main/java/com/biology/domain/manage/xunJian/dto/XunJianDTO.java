package com.biology.domain.manage.xunJian.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.xunJian.db.XunJianEntity;

import lombok.Data;

@Data
public class XunJianDTO {

    private Long xunJianId;

    private String fanWei;

    private String xunJianPinLu;

    private String xunJianLeiXing;

    private Long startTime;

    private Long endTime;

    private Boolean enable;

    public XunJianDTO(XunJianEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }

}
