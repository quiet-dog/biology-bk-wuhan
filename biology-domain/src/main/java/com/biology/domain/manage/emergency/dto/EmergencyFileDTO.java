package com.biology.domain.manage.emergency.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.emergency.db.EmergencyFileEntity;

import lombok.Data;

@Data
public class EmergencyFileDTO {
    private Long emergencyId;
    private String path;

    public EmergencyFileDTO(EmergencyFileEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
