package com.biology.domain.manage.emergencyEvent.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmEntity;
import com.biology.domain.manage.emergencyEvent.dto.EmergencyEventStaticDTO;
import com.biology.domain.manage.event.dto.EnvironmentStock;
import com.biology.domain.manage.event.dto.EventEchartDTO;

public interface EmergencyEventService extends IService<EmergencyEventEntity> {
    public EventEchartDTO getStock(EmergencyEventStaticDTO query);

    public Object getStockByHandle(EmergencyEventStaticDTO query);

}
