package com.biology.domain.manage.healthy.model;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.healthy.command.AddHealthyAlarmCommand;
import com.biology.domain.manage.healthy.db.HealthyAlarmEntity;
import com.biology.domain.manage.healthy.db.HealthyAlarmService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class HealthyAlarmModel extends HealthyAlarmEntity {
    private HealthyAlarmService healthyAlarmService;

    public HealthyAlarmModel(HealthyAlarmService healthyAlarmService) {
        this.healthyAlarmService = healthyAlarmService;
    }

    public HealthyAlarmModel(HealthyAlarmEntity entity, HealthyAlarmService healthyAlarmService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.healthyAlarmService = healthyAlarmService;
    }

    public void loadAddHealthyAlarmCommand(AddHealthyAlarmCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "healthyAlarmId");
        }
    }
}
