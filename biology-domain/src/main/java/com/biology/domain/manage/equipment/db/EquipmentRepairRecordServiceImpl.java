package com.biology.domain.manage.equipment.db;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.equipment.dto.RepairRecordEchartDTO;
import com.biology.domain.manage.equipment.query.EquipmentDataEchartDTO;
import com.biology.domain.manage.event.dto.EventEchartDTO;

@Service
public class EquipmentRepairRecordServiceImpl
        extends ServiceImpl<EquipmentRepairRecordMapper, EquipmentRepairRecordEntity>
        implements EquipmentRepairRecordService {

    @Override
    public EquipmentRepairRecordEntity getById(Long recordId) {
        return super.getById(recordId);
    }

    @Override
    // 维修记录 按照时间统计 - 月/周
    public EventEchartDTO repairRecordByTime(EquipmentDataEchartDTO query) {
        EventEchartDTO eventEchartDTO = new EventEchartDTO();
        eventEchartDTO.setType("维修记录统计");
        eventEchartDTO.setData(new ArrayList<>());
        eventEchartDTO.setTimes(new ArrayList<>());
        List<RepairRecordEchartDTO> list = new ArrayList<>();
        if (query.getDayType().equals("week") && query.getCode() != null) {
            eventEchartDTO.setTimes(DatePickUtil.getWeekNowMMDD());
            list = baseMapper.getCountByEquipmentCodeWeek(query.getCode());
        } else if (query.getDayType().equals("month") && query.getCode() != null) {
            eventEchartDTO.setTimes(DatePickUtil.getMonthNowMMDD());
            list = baseMapper.getCountByEquipmentCodeMonth(query.getCode());
        } else if (query.getDayType().equals("year") && query.getCode() != null) {
            eventEchartDTO.setTimes(DatePickUtil.getYearNowBefore());
            list = baseMapper.getCountByEquipmentCodeYear(query.getCode());
        }
        for (String time : eventEchartDTO.getTimes()) {
            boolean flag = false;
            for (RepairRecordEchartDTO repairRecordEchartDTO : list) {
                if (repairRecordEchartDTO.getTime().contains(time)) {
                    eventEchartDTO.getData().add(repairRecordEchartDTO.getCount().doubleValue());
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                eventEchartDTO.getData().add(0.0);
            }
        }

        // LocalDate today = LocalDate.now();
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // if (dayType.equals("week")) {
        // // 本周统计
        // LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        // for (int i = 0; i < 7; i++) {
        // String date = startOfWeek.plusDays(i).format(formatter);
        // eventEchartDTO.getTimes().add(date);

        // Long count = baseMapper.selectCount(new
        // LambdaQueryWrapper<EquipmentRepairRecordEntity>()
        // .eq(EquipmentRepairRecordEntity::getDeleted, false)
        // .apply("DATE_FORMAT(create_time,'%Y-%m-%d') = {0}", date));

        // eventEchartDTO.getData().add(count.doubleValue());
        // }

        // } else if (dayType.equals("month")) {
        // // 本年12个月 统计
        // LocalDate startOfYear = today.withMonth(1).withDayOfMonth(1);
        // for (int i = 0; i < 12; i++) {
        // LocalDate currentMonth = startOfYear.plusMonths(i);
        // String monthStr =
        // currentMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        // eventEchartDTO.getTimes().add(monthStr);

        // Long count = baseMapper.selectCount(new
        // LambdaQueryWrapper<EquipmentRepairRecordEntity>()
        // .eq(EquipmentRepairRecordEntity::getDeleted, false)
        // .apply("DATE_FORMAT(create_time,'%Y-%m') = {0}", monthStr));

        // eventEchartDTO.getData().add(count.doubleValue());
        // }

        // }

        return eventEchartDTO;
    }
}
