package com.biology.domain.manage.healthy.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.healthy.dto.HealthyAlarmEchartDTO;
import com.biology.domain.manage.healthy.dto.HealthyStockEchartDTO;
import com.biology.domain.manage.healthy.query.HealthyOneQuery;

public interface HealthyService extends IService<HealthyEntity> {

    public HealthyStockEchartDTO getHealthyStock(HealthyOneQuery query);
}
