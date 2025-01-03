package com.biology.domain.manage.healthy.db;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.event.dto.EventEchartDTO;
import com.biology.domain.manage.event.dto.WeekStatisticsDTO;
import com.biology.domain.manage.healthy.dto.HealthyAlarmEchartDTO;
import com.biology.domain.manage.healthy.dto.HealthyAlarmStock;
import com.biology.domain.manage.healthy.query.HealthyAlarmQuery;

@Service
public class HealthyAlarmServiceImpl extends ServiceImpl<HealthyAlarmMapper, HealthyAlarmEntity>
        implements HealthyAlarmService {

    @Override
    public List<HealthyAlarmEchartDTO> getHealthyAlarmTypeStatic(HealthyAlarmQuery query) {
        List<HealthyAlarmEchartDTO> result = new ArrayList<>();
        HealthyAlarmEchartDTO temDTO = new HealthyAlarmEchartDTO();
        temDTO.setType("温度");
        temDTO.setData(new ArrayList<>());
        temDTO.setTime(new ArrayList<>());
        // 血压
        HealthyAlarmEchartDTO bloodPressureDTO = new HealthyAlarmEchartDTO();
        bloodPressureDTO.setType("血压");
        bloodPressureDTO.setData(new ArrayList<>());
        bloodPressureDTO.setTime(new ArrayList<>());
        // LocalDate today = LocalDate.now();

        // 获取本周的开始日期（假设周一为一周的开始）
        if (query.getDayType().equals("week")) {
            bloodPressureDTO.setTime(DatePickUtil.getWeekNowMMDD());
            temDTO.setTime(DatePickUtil.getWeekNowMMDD());
            List<HealthyAlarmStock> data = this.baseMapper.getHealthyAlarmTypeStaticWeek();
            for (String s : bloodPressureDTO.getTime()) {
                boolean isTemExit = false;
                boolean isBloodExit = false;
                for (HealthyAlarmStock stock : data) {
                    if (stock.getType().contains(temDTO.getType()) && s.contains(stock.getTime())) {
                        temDTO.getData().add(stock.getCount());
                        isTemExit = true;
                    }
                    if (stock.getType().contains(bloodPressureDTO.getType()) && s.contains(stock.getTime())) {
                        bloodPressureDTO.getData().add(stock.getCount());
                        isBloodExit = true;
                    }
                }
                if (!isTemExit) {
                    temDTO.getData().add(0);
                }
                if (!isBloodExit) {
                    bloodPressureDTO.getData().add(0);
                }
            }

            // 获取今天及前6天的日期（共7天）
            // 从7天前开始，到今天结束
            // LocalDate start = today.minusDays(6);
            // for (int i = 0; i < 7; i++) {
            // LocalDate date = start.plusDays(i);
            // boolean isTemExit = false;
            // boolean isBloodExit = false;
            // temDTO.getTime().add(date.format(DateTimeFormatter.ofPattern("MM-dd")));
            // bloodPressureDTO.getTime().add(date.format(DateTimeFormatter.ofPattern("MM-dd")));
            // for (HealthyAlarmStock stock : data) {
            // if (stock.getType().contains(temDTO.getType()) &&
            // date.toString().contains(stock.getTime())) {
            // temDTO.getData().add(stock.getCount());
            // isTemExit = true;
            // }
            // if (stock.getType().contains(bloodPressureDTO.getType())
            // && stock.getTime().contains(date.toString())) {
            // bloodPressureDTO.getData().add(stock.getCount());
            // isBloodExit = true;
            // }
            // }
            // if (!isTemExit) {
            // temDTO.getData().add(0);
            // }
            // if (!isBloodExit) {
            // bloodPressureDTO.getData().add(0);
            // }
            // }

        } else if (query.getDayType().equals("month")) {
            // 获取本月的数据
            List<HealthyAlarmStock> data = this.baseMapper.getHealthyAlarmTypeStaticMonth();
            bloodPressureDTO.setTime(DatePickUtil.getMonthNowMMDD());
            temDTO.setTime(DatePickUtil.getMonthNowMMDD());
            for (String s : bloodPressureDTO.getTime()) {
                boolean isTemExit = false;
                boolean isBloodExit = false;
                for (HealthyAlarmStock stock : data) {
                    if (stock.getType().contains(temDTO.getType()) && s.contains(stock.getTime())) {
                        temDTO.getData().add(stock.getCount());
                        isTemExit = true;
                    }
                    if (stock.getType().contains(bloodPressureDTO.getType()) && s.contains(stock.getTime())) {
                        bloodPressureDTO.getData().add(stock.getCount());
                        isBloodExit = true;
                    }
                }
                if (!isTemExit) {
                    temDTO.getData().add(0);
                }
                if (!isBloodExit) {
                    bloodPressureDTO.getData().add(0);
                }
            }

            // LocalDate oneMonthAgo = today.minusMonths(1);
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
            // for (LocalDate date = oneMonthAgo; !date.isAfter(today); date =
            // date.plusDays(1)) {
            // temDTO.getTime().add(date.format(formatter));
            // bloodPressureDTO.getTime().add(date.format(formatter));
            // boolean isTemExit = false;
            // boolean isBloodExit = false;
            // for (HealthyAlarmStock stock : data) {
            // if (stock.getType().contains(temDTO.getType()) &&
            // date.toString().contains(stock.getTime())) {
            // temDTO.getData().add(stock.getCount());
            // isTemExit = true;
            // }
            // if (stock.getType().contains(bloodPressureDTO.getType())
            // && stock.getTime().contains(date.toString())) {
            // bloodPressureDTO.getData().add(stock.getCount());
            // isBloodExit = true;
            // }
            // }
            // if (!isTemExit) {
            // temDTO.getData().add(0);
            // }
            // if (!isBloodExit) {
            // bloodPressureDTO.getData().add(0);
            // }
            // }

            // YearMonth currentMonth = YearMonth.from(today);
            // 获取本月的天数
            // int lengthOfMonth = currentMonth.lengthOfMonth();
            // // 获取本月的第一天
            // LocalDate firstDay = currentMonth.atDay(1);
            // // 获取本月的最后一天
            // LocalDate lastDay = currentMonth.atEndOfMonth();
            // for (int i = 1; i <= lengthOfMonth; i++) {
            // LocalDate date = lastDay.minusDays(lengthOfMonth - i);
            // temDTO.getTime().add(date.format(DateTimeFormatter.ofPattern("MM-dd")));
            // bloodPressureDTO.getTime().add(date.format(DateTimeFormatter.ofPattern("MM-dd")));
            // boolean isTemExit = false;
            // boolean isBloodExit = false;
            // for (HealthyAlarmStock stock : data) {
            // if (stock.getType().contains(temDTO.getType()) &&
            // date.toString().contains(stock.getTime())) {
            // temDTO.getData().add(stock.getCount());
            // isTemExit = true;
            // }
            // if (stock.getType().contains(bloodPressureDTO.getType())
            // && stock.getTime().contains(date.toString())) {
            // bloodPressureDTO.getData().add(stock.getCount());
            // isBloodExit = true;
            // }
            // }
            // if (!isTemExit) {
            // temDTO.getData().add(0);
            // }
            // if (!isBloodExit) {
            // bloodPressureDTO.getData().add(0);
            // }
            // }

        } else if (query.getDayType().equals("year")) {
            // 获取本年的数据
            List<HealthyAlarmStock> data = this.baseMapper.getHealthyAlarmTypeStaticYear();
            // bloodPressureDTO.setTime(DatePickUtil.getYearNow());
            bloodPressureDTO.setTime(DatePickUtil.getYearNowBefore());
            temDTO.setTime(DatePickUtil.getYearNowBefore());
            for (String s : bloodPressureDTO.getTime()) {
                boolean isTemExit = false;
                boolean isBloodExit = false;
                for (HealthyAlarmStock stock : data) {
                    if (stock.getType().contains(temDTO.getType()) && s.equals(stock.getTime())) {
                        temDTO.getData().add(stock.getCount());
                        isTemExit = true;
                    }
                    if (stock.getType().contains(bloodPressureDTO.getType()) && s.equals(stock.getTime())) {
                        bloodPressureDTO.getData().add(stock.getCount());
                        isBloodExit = true;
                    }
                }
                if (!isTemExit) {
                    temDTO.getData().add(0);
                }
                if (!isBloodExit) {
                    bloodPressureDTO.getData().add(0);
                }
            }

            // for (int i = 1; i <= 12; i++) {
            // boolean isTemExit = false;
            // boolean isBloodExit = false;
            // temDTO.getTime().add(String.valueOf(i));
            // bloodPressureDTO.getTime().add(String.valueOf(i));
            // for (HealthyAlarmStock stock : data) {
            // if (stock.getType().contains(temDTO.getType()) &&
            // stock.getTime().equals(String.valueOf(i))) {
            // temDTO.getData().add(stock.getCount());
            // isTemExit = true;
            // }
            // if (stock.getType().contains(bloodPressureDTO.getType())
            // && stock.getTime().equals(String.valueOf(i))) {
            // bloodPressureDTO.getData().add(stock.getCount());
            // isBloodExit = true;
            // }
            // }
            // if (!isTemExit) {
            // temDTO.getData().add(0);
            // }
            // if (!isBloodExit) {
            // bloodPressureDTO.getData().add(0);
            // }
            // }

        }
        result.add(bloodPressureDTO);
        result.add(temDTO);
        return result;
    }

}
