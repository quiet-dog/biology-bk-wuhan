package com.biology.domain.manage.threshold.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface ThresholdEmergencyService extends IService<ThresholdEmergencyEntity> {
    public List<Long> getEmergencyIdsByThresholdId(Long thresholdId);
}
