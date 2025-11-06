package com.biology.domain.manage.emergencyEvent.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.emergencyEvent.dto.EmergenctHandleDTO;
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

        public Object getStockByHandle(EmergencyEventStaticDTO query) {
                Map<String, Object> result = new HashMap<>();
                List<EmergenctHandleDTO> list = baseMapper.getStockByHandle(query.getType());
                List<String> xdata = new ArrayList<>();
                List<Long> handle = new ArrayList<>();
                List<Long> unHandle = new ArrayList<>();
                for (int i = 1; i <= 12; i++) {
                        xdata.add(i + "月");
                        handle.add(0L);
                        unHandle.add(0L);
                }

                // 将数据库返回的对应月份数据填入
                for (EmergenctHandleDTO dto : list) {
                        if (dto.getTime() == null)
                                continue;
                        try {
                                int month = Integer.parseInt(dto.getTime()); // "07" -> 7
                                if (month >= 1 && month <= 12) {
                                        handle.set(month - 1, dto.getHandled() != null ? dto.getHandled() : 0L);
                                        unHandle.set(month - 1, dto.getUnHandled() != null ? dto.getUnHandled() : 0L);
                                }
                        } catch (NumberFormatException e) {
                                // 如果 time 不是数字，就跳过
                                continue;
                        }
                }

                result.put("xdata", xdata);
                result.put("handle", handle);
                result.put("unHandle", unHandle);
                // result.put("source", list);
                // result.put("query", query);
                return result;
        }
}
