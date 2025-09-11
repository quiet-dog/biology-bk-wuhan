package com.biology.domain.manage.threshold.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface ThresholdService extends IService<ThresholdEntity> {

    public ThresholdEntity selectByEquipmentId(Long equipmentId);

    public String getRunTime(List<Long> Ids);
}
