package com.biology.domain.manage.healthy.model;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.healthy.command.AddHealthyAlarmCommand;
import com.biology.domain.manage.healthy.command.AddHealthyCommand;
import com.biology.domain.manage.healthy.command.UpdateHealthyCommand;
import com.biology.domain.manage.healthy.db.HealthyAlarmService;
import com.biology.domain.manage.healthy.db.HealthyEntity;
import com.biology.domain.manage.healthy.db.HealthyService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class HealthyModel extends HealthyEntity {

    private HealthyAlarmService healthyAlarmService;

    private HealthyService healthyService;

    public HealthyModel(HealthyService healthyService, HealthyAlarmService healthyAlarmService) {
        this.healthyService = healthyService;
        this.healthyAlarmService = healthyAlarmService;
    }

    public HealthyModel(HealthyEntity entity, HealthyService healthyService, HealthyAlarmService healthyAlarmService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.healthyService = healthyService;
        this.healthyAlarmService = healthyAlarmService;
    }

    public void loadAddHealthyCommand(AddHealthyCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "healthyId");
        }
    }

    public void loadUpdateHealthyCommand(UpdateHealthyCommand command) {
        if (command != null) {
            loadAddHealthyCommand(command);
        }
    }

    public boolean checkAlarm() {
        AddHealthyAlarmCommand command = new AddHealthyAlarmCommand();
        if ((!Double.isNaN(getHighBloodPressure()) && 90 < getHighBloodPressure() && getHighBloodPressure() < 140)
                || (!Double.isNaN(getLowBloodPressure()) && 60 < getLowBloodPressure() && getLowBloodPressure() < 90)) {
            HealthyAlarmModel model = new HealthyAlarmModel();
            command.setHealthyId(getHealthyId());
            command.setType("血压异常");
            model.loadAddHealthyAlarmCommand(command);
            model.insert();
        }

        if (!Double.isNaN(getTemperature()) && (getTemperature() < 36.1 || getTemperature() > 37.2)) {
            HealthyAlarmModel model = new HealthyAlarmModel();
            command.setHealthyId(getHealthyId());
            command.setType("体温异常");
            model.loadAddHealthyAlarmCommand(command);
            model.insert();
        }
        return true;
    }

    public boolean insert() {
        super.insert();
        checkAlarm();
        return true;
    }
}
