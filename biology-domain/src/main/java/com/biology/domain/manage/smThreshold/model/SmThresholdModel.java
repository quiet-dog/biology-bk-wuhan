package com.biology.domain.manage.smThreshold.model;

import com.biology.domain.manage.smThreshold.command.AddSmThresholdCommand;
import com.biology.domain.manage.smThreshold.command.UpdateSmThresholdCommand;
import com.biology.domain.manage.smThreshold.db.SmThresholdEntity;
import com.biology.domain.manage.smThreshold.db.SmThresholdService;
import com.biology.domain.manage.smThreshold.db.SmThresholdEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SmThresholdModel extends SmThresholdEntity {

    private SmThresholdService smThresholdService;

    public SmThresholdModel(SmThresholdService smThresholdService) {
        this.smThresholdService = smThresholdService;
    }

    public SmThresholdModel(SmThresholdEntity entity, SmThresholdService smThresholdService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.smThresholdService = smThresholdService;
    }

    public void loadAddSmThresholdCommand(AddSmThresholdCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "smThresholdId");
        }
    }

    public void loadUpdateSmThresholdCommand(UpdateSmThresholdCommand command) {
        if (command != null) {
            loadAddSmThresholdCommand(command);
        }
    }

}
