package com.biology.domain.manage.smAlarm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;
import com.biology.domain.manage.smAlarm.command.AddSmAlarmCommand;
import com.biology.domain.manage.smAlarm.command.UpdateSmAlarmCommand;
import com.biology.domain.manage.smAlarm.db.SmAlarmEntity;
import com.biology.domain.manage.smAlarm.db.SmAlarmService;
import com.biology.domain.manage.smAlarm.db.SmAlarmService;
import com.biology.domain.manage.smAlarm.dto.SmAlarmDTO;
import com.biology.domain.manage.smAlarm.model.SmAlarmFactory;
import com.biology.domain.manage.smAlarm.model.SmAlarmModel;
import com.biology.domain.manage.smAlarm.query.SmAlarmQuery;
import com.biology.domain.manage.xlArchive.model.XlArchiveFactory;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmAlarmApplicationService {
    private final SmAlarmFactory smAlarmFactory;

    private final SmAlarmService smAlarmService;

    public void create(AddSmAlarmCommand command) {
        SmAlarmModel SmAlarmModel = smAlarmFactory.create();
        SmAlarmModel.loadAddSmAlarmCommand(command);
        SmAlarmModel.insert();
        return;
    }

    public void update(UpdateSmAlarmCommand command) {
        SmAlarmModel SmAlarmModel = smAlarmFactory.loadById(command.getSmAlarmId());
        SmAlarmModel.loadUpdateSmAlarmCommand(command);
        SmAlarmModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> SmAlarmIds) {
        SmAlarmIds.forEach(SmAlarmId -> {
            SmAlarmModel SmAlarmModel = smAlarmFactory.loadById(SmAlarmId);
            SmAlarmModel.deleteById();
        });
        return;
    }

    public PageDTO<SmAlarmDTO> getSmAlarms(SmAlarmQuery query) {
        Page<SmAlarmEntity> page = smAlarmService.page(query.toPage(), query.toQueryWrapper());
        List<SmAlarmDTO> records = page.getRecords().stream().map(SmAlarmDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public SmAlarmDTO getSmAlarmInfo(Long SmAlarmId) {
        SmAlarmEntity byId = smAlarmService.getById(SmAlarmId);
        return new SmAlarmDTO(byId);
    }

    public Map<String, Object> getLiShiYiChangPaiMing() {
        List<PingGuJieGuoSeriesDTO> list = smAlarmService.getLiShiYiChangPaiMing();
        Map<String, Object> result = new HashMap<>();
        List<String> xData = new ArrayList<>();
        xData.add("心率");
        xData.add("血氧");
        xData.add("体温");
        xData.add("co2");
        xData.add("呼吸");
        xData.add("心电");
        List<Object> seris = new ArrayList<>();
        for (String t : xData) {
            Boolean isExit = false;
            for (PingGuJieGuoSeriesDTO r : list) {
                if (r.getName() != null && r.getName().equals(t)) {
                    isExit = true;
                    seris.add(r.getValue());
                }
            }
            if (!isExit) {
                seris.add(0);
            }
        }

        result.put("xData", xData);
        result.put("seris", seris);
        return result;
    }

    public Map<String, Object> getBaoJingCiShuTongJiByRecentWeek() {
        List<PingGuJieGuoSeriesDTO> list = smAlarmService.getBaoJingCiShuTongJiByRecentWeek();
        Map<String, Object> result = new HashMap<>();
        List<String> xData = new ArrayList<>();
        List<Object> series = new ArrayList<>();
        for (PingGuJieGuoSeriesDTO r : list) {
            xData.add(r.getName());
            series.add(r.getValue());
        }

        result.put("xData", xData);
        result.put("series", series);
        return result;
    }

    // 获取当天异常的人数
    public long getDayExceptionCount() {
        QueryWrapper<SmAlarmEntity> queryWrapper = new QueryWrapper<SmAlarmEntity>();
        queryWrapper.eq("DATE_FORMAT(create_time, '%Y-%m-%d')", DateUtil.format(new Date(), "yyyy-MM-dd"))
                .isNotNull("type").groupBy("device_sn");
        return smAlarmService.count(queryWrapper);
    }

    public Map<String, Object> getJinRiAlarmNum() {
        Map<String, Object> result = new HashMap<>();
        Integer num = smAlarmService.getJinRiAlarmNum();
        result.put("num", num);
        return result;
    }
}
