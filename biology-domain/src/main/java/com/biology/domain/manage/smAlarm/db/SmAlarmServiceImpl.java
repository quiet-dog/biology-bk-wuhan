package com.biology.domain.manage.smAlarm.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;

@Service
public class SmAlarmServiceImpl extends ServiceImpl<SmAlarmMapper, SmAlarmEntity>
        implements SmAlarmService {

    public List<PingGuJieGuoSeriesDTO> getLiShiYiChangPaiMing() {
        return baseMapper.getLiShiYiChangPaiMing();
    }

    public List<PingGuJieGuoSeriesDTO> getBaoJingCiShuTongJiByRecentWeek() {
        return baseMapper.getBaoJingCiShuTongJiByRecentWeek();
    }

    public Integer getJinRiAlarmNum() {
        return baseMapper.getJinRiAlarmNum();
    }

    public Integer getTodayAlarmNum() {
        return baseMapper.getTodayAlarmNum();
    }
}
