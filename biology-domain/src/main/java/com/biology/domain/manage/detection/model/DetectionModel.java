package com.biology.domain.manage.detection.model;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.detection.db.DetectionEntity;
import com.biology.domain.manage.detection.db.DetectionService;
import com.biology.domain.manage.environment.db.EnvironmentEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DetectionModel extends DetectionEntity {
    private DetectionService detectionService;

    public DetectionModel(DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    public DetectionModel(DetectionEntity entity, DetectionService detectionService) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.detectionService = detectionService;
    }

    public void loadAddDetectionCommand(AddDetectionCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "detectionId");
        }
    }

    public void loadUpdateDetectionCommand(AddDetectionCommand command) {
        if (command != null) {
            loadAddDetectionCommand(command);
        }
    }

    public boolean insert() {
        EnvironmentEntity environmentEntity = new EnvironmentEntity().selectById(getEnvironmentId());
        if (environmentEntity.getUnitName() != null) {
            this.setType(environmentEntity.getUnitName());
        }
        super.insert();

        return true;
    }
}
