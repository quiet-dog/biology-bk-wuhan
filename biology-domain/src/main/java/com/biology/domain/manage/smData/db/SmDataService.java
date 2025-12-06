package com.biology.domain.manage.smData.db;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

public interface SmDataService extends IService<SmDataEntity> {

    public List<Map<String, Object>> selectBatteryHistory(Long smDeviceId, String startTime, String endTime);

    public List<Map<String, Object>> selectCo2History(Long smDeviceId, String startTime, String endTime);

    public List<Map<String, Object>> selectHumilityHistory(Long smDeviceId, String startTime, String endTime);

    public List<Map<String, Object>> selectTempHistory(Long smDeviceId, String startTime, String endTime);

    public List<Map<String, Object>> selectXinlvHistory(Long smDeviceId, String startTime, String endTime);

    public List<Map<String, Object>> selectXueYangHistory(Long smDeviceId, String startTime, String endTime);

    public List<Map<String, Object>> selectIsOnlineHistory(Long smDeviceId, String startTime, String endTime);

}
