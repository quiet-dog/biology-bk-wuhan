package com.biology.domain.manage.healthyMoni.model;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.healthy.command.AddHealthyCommand;
import com.biology.domain.manage.healthy.db.HealthyAlarmService;
import com.biology.domain.manage.healthy.db.HealthyEntity;
import com.biology.domain.manage.healthy.db.HealthyService;
import com.biology.domain.manage.healthyMoni.command.AddHealthyMoniCommand;
import com.biology.domain.manage.healthyMoni.command.UpdateHealthyMoniCommand;
import com.biology.domain.manage.healthyMoni.db.HealthyMoniEntity;
import com.biology.domain.manage.healthyMoni.db.HealthyMoniService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class HealthyMoniModel extends HealthyMoniEntity {

    private HealthyMoniService healthyMoniService;

    public HealthyMoniModel(HealthyMoniService healthyMoniService) {
        this.healthyMoniService = healthyMoniService;
    }

    public HealthyMoniModel(HealthyMoniEntity entity, HealthyMoniService healthyMoniService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.healthyMoniService = healthyMoniService;
    }

    public void loadAddHealthyMoniCommand(AddHealthyMoniCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "healthyMoniId");
        }
    }

    public void loadUpdateHealthyMoniCommand(UpdateHealthyMoniCommand command) {
        if (command != null) {
            loadAddHealthyMoniCommand(command);
        }
    }
}
