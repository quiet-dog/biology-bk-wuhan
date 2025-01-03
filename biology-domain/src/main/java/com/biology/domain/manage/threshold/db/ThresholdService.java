package com.biology.domain.manage.threshold.db;

import com.baomidou.mybatisplus.extension.service.IService;

public interface ThresholdService extends IService<ThresholdEntity> {

    public ThresholdEntity selectByEquipmentId(Long equipmentId);
}
