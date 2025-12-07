package com.biology.domain.manage.xunJianHistory.db;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

public interface XunJianEventService extends IService<XunJianEventEntity> {

    // 统计最近7天每天的报警数量,只需要数量和日期
    public List<Map<String, Object>> getAlarmCountByRecent7Days();

    // 最近30天每天的报警数量,只需要数量和日期
    public List<Map<String, Object>> getAlarmCountByRecent30Days();

    // 最近一年每天的报警数量,只需要数量和日期
    public List<Map<String, Object>> getAlarmCountByRecent1Year();
}
