package com.biology.domain.manage.smAlarm.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.smAlarm.db.SmAlarmEntity;

import lombok.Data;

@Data
public class SmAlarmDTO {
    private String type;

    private String description;

    private String deviceSn;

    private Long smAlarmId;

    public SmAlarmDTO(SmAlarmEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
    }
}
