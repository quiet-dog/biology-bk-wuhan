package com.biology.domain.manage.threshold.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ThresholdServiceImpl extends ServiceImpl<ThresholdMapper, ThresholdEntity> implements ThresholdService {

    @Override
    public ThresholdEntity selectByEquipmentId(Long equipmentId) {
        QueryWrapper<ThresholdEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("equipment_id", equipmentId);
        return baseMapper.selectOne(queryWrapper);
    }

}
