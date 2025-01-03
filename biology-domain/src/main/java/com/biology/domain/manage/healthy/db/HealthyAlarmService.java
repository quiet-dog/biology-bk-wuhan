package com.biology.domain.manage.healthy.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.healthy.dto.HealthyAlarmEchartDTO;
import com.biology.domain.manage.healthy.dto.HealthyAlarmStock;
import com.biology.domain.manage.healthy.query.HealthyAlarmQuery;

public interface HealthyAlarmService extends IService<HealthyAlarmEntity> {

    public List<HealthyAlarmEchartDTO> getHealthyAlarmTypeStatic(HealthyAlarmQuery query);
}
