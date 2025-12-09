package com.biology.domain.manage.xunJianHistory.db;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.xunJianHistory.dto.EchartDataDTO;

public interface XunJianEventService extends IService<XunJianEventEntity> {

    // 统计最近7天每天的报警数量,只需要数量和日期
    public List<EchartDataDTO> getAlarmCountByRecent7Days();

    // 最近30天每天的报警数量,只需要数量和日期
    public List<EchartDataDTO> getAlarmCountByRecent30Days();

    // 最近一年每天的报警数量,只需要数量和日期
    public List<EchartDataDTO> getAlarmCountByRecent1Year();
}
