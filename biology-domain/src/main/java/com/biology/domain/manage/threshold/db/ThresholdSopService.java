package com.biology.domain.manage.threshold.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface ThresholdSopService extends IService<ThresholdSopEntity> {
    public List<Long> getSopIdsByThresholdId(Long thresholdId);
}
