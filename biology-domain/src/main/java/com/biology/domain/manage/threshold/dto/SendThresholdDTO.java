package com.biology.domain.manage.threshold.dto;

import java.util.List;

import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;

import lombok.Data;

@Data
public class SendThresholdDTO {
    private ThresholdEntity threshold;
    private List<ThresholdValueEntity> thresholdValues;
}
