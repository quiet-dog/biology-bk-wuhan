package com.biology.domain.manage.equipment.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, EquipmentEntity>
        implements EquipmentService {

    @Override
    public EquipmentEntity getById(Long equipmentId) {
        return super.getById(equipmentId);
    }

    @Override
    public String getEquipmentNameByCode(String equipmentCode) {
        return baseMapper.selectOne(new QueryWrapper<EquipmentEntity>().eq("equipment_code", equipmentCode))
                .getEquipmentName();
    }

    @Override
    public EquipmentEntity getByEquipmentCode(String equipmentCode) {
        return baseMapper.selectOne(new QueryWrapper<EquipmentEntity>().eq("equipment_code", equipmentCode));
    }

    @Override
    public List<EquipmentEntity> listByEquipmentIds(List<Long> equipmentIds) {
        if (equipmentIds == null || equipmentIds.isEmpty()) {
            return new ArrayList<>();
        }

        return baseMapper.selectList(
                new QueryWrapper<EquipmentEntity>()
                        .in("equipment_id", equipmentIds));
    }

    @Override
    public Long getAlarmCountByDay() {
        return baseMapper.getAlarmCountByDay();
    }

    @Override
    public Long getAlarmCountByMonth() {
        return baseMapper.getAlarmCountByMonth();
    }

    @Override
    public Long getAlarmCountByYear() {
        return baseMapper.getAlarmCountByYear();
    }
}
