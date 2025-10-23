package com.biology.domain.manage.runTime.model;

import com.biology.domain.manage.runTime.command.AddRunTimeCommand;
import com.biology.domain.manage.runTime.command.UpdateRunTimeCommand;
import com.biology.domain.manage.runTime.db.RunTimeEntity;
import com.biology.domain.manage.runTime.db.RunTimeService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RunTimeModel extends RunTimeEntity {
    private RunTimeService runTimeService;

    public RunTimeModel(RunTimeService runTimeService) {
        this.runTimeService = runTimeService;
    }

    public RunTimeModel(RunTimeEntity entity, RunTimeService runTimeService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.runTimeService = runTimeService;
    }

    public void loadAddRunTimeCommand(AddRunTimeCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "runTimeId");
        }
    }

    public void loadUpdateRunTimeCommand(UpdateRunTimeCommand command) {
        if (command != null) {
            loadAddRunTimeCommand(command);
        }
    }
}
