package com.biology.domain.manage.xsData.db;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

public interface XsDataService extends IService<XsDataEntity> {

    public List<Map<String, Object>> selectIsOnlineHistory(String xsDeviceId, String startTime, String endTime);
}
