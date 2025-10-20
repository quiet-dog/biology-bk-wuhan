package com.biology.domain.manage.dianShui.model;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.detection.command.UpdateDetectionCommand;
import com.biology.domain.manage.dianShui.db.DianShuiEntity;
import com.biology.domain.manage.dianShui.db.DianShuiService;
import com.biology.domain.manage.dianShui.db.DianShuiEntity;
import com.biology.domain.manage.environment.db.EnvironmentEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DianShuiModel extends DianShuiEntity {
    private DianShuiService dianShuiService;

    public DianShuiModel(DianShuiService dianShuiService) {
        this.dianShuiService = dianShuiService;
    }

    public DianShuiModel(DianShuiEntity entity, DianShuiService dianShuiService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.dianShuiService = dianShuiService;
    }

    public void loadAddDianShuiCommand(AddDetectionCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "detectionId");
        }
    }

    public void loadUpdateDianShuiCommand(UpdateDetectionCommand command) {
        if (command != null) {
            loadAddDianShuiCommand(command);
        }
    }

    public boolean insert() {

        super.insert();

        return true;
    }
}
