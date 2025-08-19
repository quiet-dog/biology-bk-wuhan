package com.biology.domain.manage.xwAlarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoEchart;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;
import com.biology.domain.manage.xwAlarm.command.AddXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.command.UpdateXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.db.XwAlarmEntity;
import com.biology.domain.manage.xwAlarm.db.XwAlarmService;
import com.biology.domain.manage.xwAlarm.dto.XingWeiDTO;
import com.biology.domain.manage.xwAlarm.dto.XwAlarmDTO;
import com.biology.domain.manage.xwAlarm.model.XwAlarmFactory;
import com.biology.domain.manage.xwAlarm.model.XwAlarmModel;
import com.biology.domain.manage.xwAlarm.query.XwAlarmQuery;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XwAlarmApplicationService {
    private final XwAlarmFactory xwAlarmFactory;

    private final XwAlarmService xwAlarmService;

    public void create(AddXwAlarmCommand command) {
        XwAlarmModel xwAlarmModel = xwAlarmFactory.create();
        xwAlarmModel.loadAddXwAlarmCommand(command);
        xwAlarmModel.insert();
        return;
    }

    public void update(UpdateXwAlarmCommand command) {
        XwAlarmModel xwAlarmModel = xwAlarmFactory.loadById(command.getXwAlarmId());
        xwAlarmModel.loadUpdateXwAlarmCommand(command);
        xwAlarmModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> xwAlarmIds) {
        xwAlarmIds.forEach(xwAlarmId -> {
            XwAlarmModel xwAlarmModel = xwAlarmFactory.loadById(xwAlarmId);
            xwAlarmModel.deleteById();
        });
        return;
    }

    public PageDTO<XwAlarmDTO> getXwAlarms(XwAlarmQuery query) {
        Page<XwAlarmEntity> page = xwAlarmService.page(query.toPage(), query.toQueryWrapper());
        List<XwAlarmDTO> records = page.getRecords().stream().map(XwAlarmDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public XwAlarmDTO getXwAlarmInfo(Long xwAlarmId) {
        XwAlarmEntity byId = xwAlarmService.getById(xwAlarmId);
        return new XwAlarmDTO(byId);
    }

    public void getXingWeiAlarm(XingWeiDTO xingWeiDTO) {
        AddXwAlarmCommand command = new AddXwAlarmCommand();
        command.setCameraId(xingWeiDTO.getCameraId());
        command.setAlarmId(xingWeiDTO.getAlarmId());
        command.setDisplayFlag(xingWeiDTO.getDisplayFlag());
        command.setFilterFlag(xingWeiDTO.getFilterFlag());
        command.setFlag(xingWeiDTO.getFlag());
        command.setFunctionType(xingWeiDTO.getFunctionType());
        command.setPicPath(xingWeiDTO.getPicPath());
        command.setPicPathOrg(xingWeiDTO.getPicPathOrg());
        command.setSeatNumber(xingWeiDTO.getSeatNumber());
        command.setTimeStamp(xingWeiDTO.getTimeStampAsMillis());
        create(command);
    }

    public PingGuJieGuoEchart JiWeiBaoJingZhanBi(String dayType) {
        PingGuJieGuoEchart pEchart = new PingGuJieGuoEchart();
        if (dayType != null) {
            List<PingGuJieGuoSeriesDTO> list = new ArrayList<>();
            if (dayType.equals("week")) {
                list = xwAlarmService.JiWeiBaoJingZhanBiByWeek(dayType);
            }
            if (dayType.equals("month")) {
                list = xwAlarmService.JiWeiBaoJingZhanBiByMonth(dayType);
            }
            if (dayType.equals("year")) {
                list = xwAlarmService.JiWeiBaoJingZhanBiByYear(dayType);
            }
            pEchart.setSeries(list);
        }

        return pEchart;
    }

    public Map<String, Object> JianCeShuJuTongJi() {
        List<PingGuJieGuoSeriesDTO> list = xwAlarmService.JianCeShuJuTongJi();
        Map<String, Object> result = new HashMap<>();
        List<Object> xData = new ArrayList<>();
        List<Object> sData = new ArrayList<>();
        for (PingGuJieGuoSeriesDTO r : list) {
            xData.add(r.getName());
            sData.add(r.getValue());
        }
        result.put("xData", xData);
        result.put("sData", sData);

        return result;
    }

    public Map<String, Object> jiWeiQuShiBianHua(String dayType, String seatNumber) {
        Map<String, Object> result = new HashMap<>();
        List<Object> sData = new ArrayList<>();
        List<String> xData = new ArrayList<>();
        List<PingGuJieGuoSeriesDTO> list = new ArrayList<>();
        if (dayType != null) {

            if (dayType.equals("day")) {
                if (!StrUtil.isEmpty(seatNumber)) {
                    list = xwAlarmService.jiWeiQuShiBianHuaByDay(seatNumber);
                } else {
                    list = xwAlarmService.jiWeiQuShiBianHuaByDayAll();
                }
            } else if (dayType.equals("week")) {
                if (!StrUtil.isEmpty(seatNumber)) {
                    list = xwAlarmService.jiWeiQuShiBianHuaByWeek(seatNumber);
                } else {
                    list = xwAlarmService.jiWeiQuShiBianHuaByWeekAll();
                }
            } else if (dayType.equals("month")) {
                if (!StrUtil.isEmpty(seatNumber)) {
                    list = xwAlarmService.jiWeiQuShiBianHuaByMonth(seatNumber);
                } else {
                    list = xwAlarmService.jiWeiQuShiBianHuaByMonthAll();
                }
            } else if (dayType.equals("year")) {
                if (!StrUtil.isEmpty(seatNumber)) {
                    list = xwAlarmService.jiWeiQuShiBianHuaByYear(seatNumber);
                } else {
                    list = xwAlarmService.jiWeiQuShiBianHuaByYearAll();
                }
            }

        }
        for (

        PingGuJieGuoSeriesDTO r : list) {
            xData.add(r.getName());
            sData.add(r.getValue());
        }

        result.put("xData", xData);
        result.put("sData", sData);

        return result;
    }
}
