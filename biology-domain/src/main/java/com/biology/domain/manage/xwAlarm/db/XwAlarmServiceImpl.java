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

    public List<PingGuJieGuoSeriesDTO> JianCeShuJuTongJi() {
        return baseMapper.JianCeShuJuTongJi();
    }

    @Override
    public List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByWeek(String dayType) {
        return baseMapper.jiWeiQuShiBianHuaByWeek(dayType);
    }

    @Override
    public List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByWeekAll() {
        return baseMapper.jiWeiQuShiBianHuaByWeekAll();
    }

    @Override
    public List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByMonth(String dayType) {
        return baseMapper.jiWeiQuShiBianHuaByMonth(dayType);
    }

    @Override
    public List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByMonthAll() {
        return baseMapper.jiWeiQuShiBianHuaByMonthAll();
    }

    @Override
    public List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByYear(String dayType) {
        return baseMapper.jiWeiQuShiBianHuaByYear(dayType);
    }

    @Override
    public List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByYearAll() {
        return baseMapper.jiWeiQuShiBianHuaByYearAll();
    }

    @Override
    public List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByDay(String dayType) {
        return baseMapper.jiWeiQuShiBianHuaByDay(dayType);
    }

    @Override
    public List<PingGuJieGuoSeriesDTO> jiWeiQuShiBianHuaByDayAll() {
        return baseMapper.jiWeiQuShiBianHuaByDayAll();
    }

    public Integer getJinRiBaoJingNum() {
        return baseMapper.getJinRiBaoJingNum();
    }
}
