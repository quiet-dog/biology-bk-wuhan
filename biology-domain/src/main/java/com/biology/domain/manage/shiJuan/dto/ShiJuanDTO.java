package com.biology.domain.manage.shiJuan.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ShiJuanEntity;

import lombok.Data;

@Data
public class ShiJuanDTO {
    private Long xlShiJuanId;

    private String type;

    private String name;

    private Long sort;

    public ShiJuanDTO(ShiJuanEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
