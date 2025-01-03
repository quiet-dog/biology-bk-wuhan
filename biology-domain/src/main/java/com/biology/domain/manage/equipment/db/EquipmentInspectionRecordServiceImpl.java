package com.biology.domain.manage.equipment.db;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class EquipmentInspectionRecordServiceImpl extends ServiceImpl<EquipmentInspectionRecordMapper, EquipmentInspectionRecordEntity>
        implements EquipmentInspectionRecordService {

    @Override
    public EquipmentInspectionRecordEntity getById(Long recordId) {
        return super.getById(recordId);
    }
}