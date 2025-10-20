package com.biology.domain.manage.healthyMoni.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.healthyMoni.db.HealthyMoniEntity;

import lombok.Data;

@Data
public class HealthyMoniDTO {
    private Long healthyMoniId;

    private Long personnelId;

    private String fieldType;

    private Double min;

    private Double max;

    private Integer pushFrequency;

    private Boolean isPush;

    public HealthyMoniDTO(HealthyMoniEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
