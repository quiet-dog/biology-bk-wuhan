package com.biology.domain.manage.xwAlarm.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;

import io.lettuce.core.dynamic.annotation.Param;

@Service
public class XwAlarmServiceImpl extends ServiceImpl<XwAlarmMapper, XwAlarmEntity> implements XwAlarmService {

    public List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByWeek(String dayType) {
        return baseMapper.JiWeiBaoJingZhanBiByWeek(dayType);
    }

    public List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByYear(String dayType) {
        return baseMapper.JiWeiBaoJingZhanBiByYear(dayType);
    }

    public List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByMonth(String dayType) {
        return baseMapper.JiWeiBaoJingZhanBiByMonth(dayType);
    }
}
