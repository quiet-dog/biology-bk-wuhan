package com.biology.domain.manage.nongDuData.db;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

public interface NongDuDataService extends IService<NongDuDataEntity> {

    public List<Map<String, Object>> selectIsOnlineHistory(String deviceSn, String startTime, String endTime);

    public List<Map<String, Object>> selectIsOnlineHistoryIsNull(String startTime, String endTime);

    public Integer getAlarmCount(String startTime, String endTime, String deviceSn);

    public Integer getJinRiBaoJing();
}
