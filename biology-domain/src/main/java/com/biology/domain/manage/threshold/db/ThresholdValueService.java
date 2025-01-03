package com.biology.domain.manage.threshold.db;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface ThresholdValueService extends IService<ThresholdValueEntity> {
    public List<ThresholdValueEntity> selectByThresholdId(Long thresholdId);

    public List<String> getTypeList();
}
