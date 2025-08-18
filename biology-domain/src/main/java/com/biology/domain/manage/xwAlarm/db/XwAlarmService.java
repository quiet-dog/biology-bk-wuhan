package com.biology.domain.manage.xwAlarm.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;

import io.lettuce.core.dynamic.annotation.Param;

public interface XwAlarmService extends IService<XwAlarmEntity> {

    public List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByWeek(String dayType);

    public List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByYear(String dayType);

    public List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByMonth(String dayType);
}
