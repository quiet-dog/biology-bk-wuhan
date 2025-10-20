package com.biology.domain.manage.emergencyEvent.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.emergencyEvent.dto.EmergencyEventStaticDTO;
import com.biology.domain.manage.event.dto.EnvironmentStock;
import com.biology.domain.manage.event.dto.EventEchartDTO;

@Service
public class EmergencyEventServiceImpl extends ServiceImpl<EmergencyEventMapper, EmergencyEventEntity>
                implements EmergencyEventService {

        public EventEchartDTO getStock(EmergencyEventStaticDTO query) {
                List<EnvironmentStock> list = baseMapper.getStock(query.getType());
                EventEchartDTO result = new EventEchartDTO();
                result.setData(new ArrayList<>());
                result.setTimes(new ArrayList<>());
                // 获取今年的所有月份1-12
                for (int i = 1; i <= 12; i++) {
                        result.getTimes().add(i + "月");
                        Boolean isExist = false;
                        for (EnvironmentStock stock : list) {
                                if (stock.getTime().equals(i < 10 ? ("0" + i + "") : String.valueOf(i))) {
                                        isExist = true;
                                        result.getData().add(stock.getCount().doubleValue());
                                        break;
                                }
                        }
                        if (!isExist) {
                                result.getData().add(0.0);
                        }
                }
                return result;
        }
}
