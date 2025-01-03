package com.biology.domain.manage.healthy.db;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.healthy.dto.HealthyAlarmEchartDTO;
import com.biology.domain.manage.healthy.dto.HealthyStock;
import com.biology.domain.manage.healthy.dto.HealthyStockEchartDTO;
import com.biology.domain.manage.healthy.query.HealthyAlarmQuery;
import com.biology.domain.manage.healthy.query.HealthyOneQuery;

@Service
public class HealthyServiceImpl extends ServiceImpl<HealthyMapper, HealthyEntity> implements HealthyService {

    @Override
    public HealthyStockEchartDTO getHealthyStock(HealthyOneQuery query) {
        List<HealthyStock> list = new ArrayList<>();
        HealthyStockEchartDTO hDto = new HealthyStockEchartDTO();
        hDto.setTime(new ArrayList<>());
        hDto.setData(new ArrayList<>());
        if (query.getType().equals("温度")) {
            list = baseMapper.getTemperatureWeek(query.getPersonnelId());
        } else if (query.getType().equals("收缩压")) {
            list = baseMapper.getHighBloodPressureWeek(query.getPersonnelId());
        } else if (query.getType().equals("舒张压")) {
            list = baseMapper.getLowBloodPressureWeek(query.getPersonnelId());
        } else if (query.getType().equals("心率")) {
            list = baseMapper.getHeartRateWeek(query.getPersonnelId());
        }

        for (HealthyStock healthyStock : list) {
            hDto.getTime().add(healthyStock.getTime());
            hDto.getData().add(healthyStock.getData());
        }

        // hDto.setTime(DatePickUtil.getWeekNowMMDD());
        // for (String s : hDto.getTime()) {
        // boolean isExit = false;
        // for (HealthyStock healthyStock : list) {
        // if (healthyStock.getTime().contains(s)) {
        // hDto.getData().add(healthyStock.getData());
        // isExit = true;
        // break;
        // }
        // }
        // if (!isExit) {
        // hDto.getData().add(0.0);
        // }
        // }

        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        // LocalDate today = LocalDate.now();
        // LocalDate start = today.minusDays(6);
        // for (int i = 0; i < 7; i++) {
        // LocalDate date = start.plusDays(i);
        // hDto.getTime().add(date.format(formatter));
        // boolean isExit = false;
        // for (HealthyStock healthyStock : list) {
        // if (healthyStock.getTime().contains(date.format(formatter))) {
        // hDto.getData().add(healthyStock.getData());
        // isExit = true;
        // break;
        // }
        // }
        // if (!isExit) {
        // hDto.getData().add(0.0);
        // }
        // }
        return hDto;
    }

}
