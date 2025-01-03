package com.biology.domain.manage.detection.dto;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;

import lombok.Data;

@Data
public class StatisticsDetailDTO {
    private Long environmentId;

    private EnvironmentDTO environment;

    private List<DetectionStatisticsDTO> data;

    public StatisticsDetailDTO(Long environmentId, List<DetectionStatisticsDTO> data) {
        this.environmentId = environmentId;
        this.data = data;
        if (getEnvironmentId() != null) {
            EnvironmentEntity environmentEntity = new EnvironmentEntity();
            this.environment = new EnvironmentDTO(environmentEntity.selectById(getEnvironmentId()));
        }
    }
}
