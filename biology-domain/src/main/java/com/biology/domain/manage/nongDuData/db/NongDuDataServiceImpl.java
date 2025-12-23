package com.biology.domain.manage.nongDuData.db;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class NongDuDataServiceImpl extends ServiceImpl<NongDuDataMapper, NongDuDataEntity>
                implements NongDuDataService {

        @Override
        public List<Map<String, Object>> selectIsOnlineHistory(String deviceSn, String startTime, String endTime) {
                return baseMapper.selectIsOnlineHistory(deviceSn, startTime, endTime);
        }

        @Override
        public List<Map<String, Object>> selectIsOnlineHistoryIsNull(String startTime, String endTime) {
                return baseMapper.selectIsOnlineHistoryIsNull(startTime, endTime);
        }

        @Override
        public Integer getAlarmCount(String startTime, String endTime, String deviceSn) {
                return baseMapper.getAlarmCount(startTime, endTime, deviceSn);
        }

        @Override
        public Integer getJinRiBaoJing() {
                return baseMapper.getJinRiBaoJing();
        }
}
