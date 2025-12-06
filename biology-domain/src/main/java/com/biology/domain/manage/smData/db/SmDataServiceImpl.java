package com.biology.domain.manage.smData.db;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class SmDataServiceImpl extends ServiceImpl<SmDataMapper, SmDataEntity> implements SmDataService {

    @Override
    public List<Map<String, Object>> selectBatteryHistory(Long smDeviceId, String startTime, String endTime) {
        return baseMapper.selectBatteryHistory(smDeviceId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> selectCo2History(Long smDeviceId, String startTime, String endTime) {
        return baseMapper.selectCo2History(smDeviceId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> selectHumilityHistory(Long smDeviceId, String startTime, String endTime) {
        return baseMapper.selectHumilityHistory(smDeviceId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> selectTempHistory(Long smDeviceId, String startTime, String endTime) {
        return baseMapper.selectTempHistory(smDeviceId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> selectXinlvHistory(Long smDeviceId, String startTime, String endTime) {
        return baseMapper.selectXinlvHistory(smDeviceId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> selectXueYangHistory(Long smDeviceId, String startTime, String endTime) {
        return baseMapper.selectXueYangHistory(smDeviceId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> selectIsOnlineHistory(Long smDeviceId, String startTime, String endTime) {
        return baseMapper.selectIsOnlineHistory(smDeviceId, startTime, endTime);
    }
}
