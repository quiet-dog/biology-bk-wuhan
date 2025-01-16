package com.biology.domain.manage.equipment.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;
import com.biology.domain.manage.event.query.AreaStatisticsQuery;

public interface EquipmentDailyInspectionRecordService extends IService<EquipmentDailyInspectionRecordEntity> {
    // 在此处定义其他业务逻辑方法
    public EquipmentDataStockEchartDTO getCishu(AreaStatisticsQuery query);
}