package com.biology.domain.manage.smAlarm.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;

public interface SmAlarmService extends IService<SmAlarmEntity> {

    public List<PingGuJieGuoSeriesDTO> getLiShiYiChangPaiMing();

    public List<PingGuJieGuoSeriesDTO> getBaoJingCiShuTongJiByRecentWeek();

    public Integer getJinRiAlarmNum();
}
