package com.biology.domain.manage.event.db;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.detection.dto.DareaDTO;
import com.biology.domain.manage.detection.dto.DareaResultDTO;
import com.biology.domain.manage.detection.dto.SeriesDTO;
import com.biology.domain.manage.event.dto.AllEventEchartDTO;
import com.biology.domain.manage.event.dto.AreaStatisticsDTO;
import com.biology.domain.manage.event.dto.EnvironmentStock;
import com.biology.domain.manage.event.dto.EnvironmentStockEchart;
import com.biology.domain.manage.event.dto.EventEchartDTO;
import com.biology.domain.manage.event.dto.EventEchartOneDTO;
import com.biology.domain.manage.event.dto.EventTotalDTO;
import com.biology.domain.manage.event.dto.HistoryEventAll;
import com.biology.domain.manage.event.dto.HistoryEventEchart;
import com.biology.domain.manage.event.dto.WeekStatisticsDTO;
import com.biology.domain.manage.event.query.AreaStatisticsQuery;
import com.biology.domain.manage.event.query.EnvironmentEventQuery;
import com.biology.domain.manage.event.query.StatisticsQuery;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, EventEntity> implements EventService {
    public List<WeekStatisticsDTO> getWeekStatistics(String dayType) {
        if ("week".equals(dayType)) {
            return baseMapper.getNowWeekStatistics();
        } else if ("month".equals(dayType)) {
            return baseMapper.getNowMonthStatistics();
        } else if ("year".equals(dayType)) {
            return baseMapper.getNowYearStatistics();
        }
        return null;
    }

    @Override
    public List<AreaStatisticsDTO> getAreaStatistics(AreaStatisticsQuery query) {
        return baseMapper.getAreaStatistics(query.getStartTime(), query.getEndTime());
    }

    @Override
    public EventTotalDTO getEventTotal() {
        EventTotalDTO eDto = new EventTotalDTO();
        Integer todayTotal = baseMapper.getTodayAlarmCount();
        Integer allTotal = baseMapper.getAlarmCount();
        eDto.setTodayTotal(todayTotal);
        eDto.setAllTotal(allTotal);
        return eDto;
    }

    public HistoryEventEchart getHistoryEventAll(StatisticsQuery query) {
        HistoryEventEchart eDto = new HistoryEventEchart();
        eDto.setTime(new ArrayList<>());
        eDto.setData(new ArrayList<>());
        // LocalDate today = LocalDate.now();
        if (query.getDayType().equals("week")) {
            eDto.setTime(DatePickUtil.getWeekNowMMDD());
            List<HistoryEventAll> list = baseMapper.getHistoryEventAllWeek();
            for (String date : eDto.getTime()) {
                Boolean isExit = false;
                for (HistoryEventAll historyEventAll : list) {
                    if (date.contains(historyEventAll.getTime())) {
                        eDto.getData().add(historyEventAll.getCount());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    eDto.getData().add(0);
                }
            }

            // LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // for (int i = 0; i < 7; i++) {
            // String date = startOfWeek.plusDays(i).format(formatter);
            // eDto.getTime().add(date);
            // Boolean isExit = false;
            // for (HistoryEventAll historyEventAll : list) {
            // if (date.contains(historyEventAll.getTime())) {
            // eDto.getData().add(historyEventAll.getCount());
            // isExit = true;
            // }
            // }
            // if (!isExit) {
            // eDto.getData().add(0);
            // }
            // }
        } else if (query.getDayType().equals("month"))

        {
            eDto.setTime(DatePickUtil.getMonthNowMMDD());
            List<HistoryEventAll> list = baseMapper.getHistoryEventAllMonth();
            for (String date : eDto.getTime()) {
                Boolean isExit = false;
                for (HistoryEventAll historyEventAll : list) {
                    if (date.contains(historyEventAll.getTime())) {
                        eDto.getData().add(historyEventAll.getCount());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    eDto.getData().add(0);
                }
            }

            // LocalDate startOfMonth = LocalDate.of(today.getYear(), today.getMonth(), 1);
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 获取前一个月的日期
            // LocalDate oneMonthAgo = today.minusMonths(1);
            // // 定义日期格式
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // for (LocalDate date = oneMonthAgo; !date.isAfter(today); date =
            // date.plusDays(1)) {
            // String dateStr = date.format(formatter);
            // eDto.getTime().add(dateStr);
            // Boolean isExit = false;
            // for (HistoryEventAll historyEventAll : list) {
            // if (dateStr.contains(historyEventAll.getTime())) {
            // eDto.getData().add(historyEventAll.getCount());
            // isExit = true;
            // }
            // }
            // if (!isExit) {
            // eDto.getData().add(0);
            // }
            // }
        } else if (query.getDayType().equals("year")) {
            // eDto.setTime(DatePickUtil.getYearNow());getYearNowBefore
            eDto.setTime(DatePickUtil.getYearNowBefore());
            List<HistoryEventAll> list = baseMapper.getHistoryEventAllYear();

            for (String date : eDto.getTime()) {
                Boolean isExit = false;
                for (HistoryEventAll historyEventAll : list) {
                    if (historyEventAll.getTime().equals(date)) {
                        eDto.getData().add(historyEventAll.getCount());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    eDto.getData().add(0);
                }
            }

            // LocalDate startOfYear = LocalDate.of(today.getYear(), 1, 1);
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // List<HistoryEventAll> list = baseMapper.getHistoryEventAllYear();
            // for (int i = 0; i < 12; i++) {
            // String date = startOfYear.plusMonths(i).format(formatter);
            // eDto.getTime().add(date);
            // Boolean isExit = false;
            // for (HistoryEventAll historyEventAll : list) {
            // if (date.contains(historyEventAll.getTime())) {
            // eDto.getData().add(historyEventAll.getCount());
            // isExit = true;
            // }
            // }
            // if (!isExit) {
            // eDto.getData().add(0);
            // }
            // }
        }
        return eDto;
    }

    public EnvironmentStockEchart getEnvrionmentEventAllWeek(EnvironmentEventQuery query) {
        EnvironmentStockEchart eDto = new EnvironmentStockEchart();
        eDto.setDatas(new ArrayList<>());
        eDto.setUnitNames(new ArrayList<>());
        List<String> unitNames = baseMapper.getEnvironmentUnitAll();
        for (String unitName : unitNames) {
            if (unitName.equals("电") || unitName.equals("水")) {
                continue;
            }
            eDto.getUnitNames().add(unitName);
            eDto.getDatas().add(0);
        }
        List<EnvironmentStock> list = new ArrayList<>();
        if (query.getDayType().equals("week")) {
            // if (!query.getDescription().equals("") && query.getDescription().isEmpty()) {
            list = baseMapper.getEnvrionmentEventAllWeek();
            // } else {
            // list = baseMapper.getEnvrionmentEventAllWeek(query.getDescription());
            // }
        } else if (query.getDayType().equals("month")) {
            // if (!query.getDescription().equals("") && query.getDescription().isEmpty()) {
            list = baseMapper.getEnvrionmentEventAllMonth();
            // } else {
            // list = baseMapper.getEnvrionmentEventAllMonth(query.getDescription());
            // }
        } else if (query.getDayType().equals("year")) {
            // if (!query.getDescription().equals("") && query.getDescription().isEmpty()) {
            list = baseMapper.getEnvrionmentEventAllYear();
            // } else {
            // list = baseMapper.getEnvrionmentEventAllYear(query.getDescription());
            // }
        }
        for (EnvironmentStock environmentStock : list) {
            for (String unitName : eDto.getUnitNames()) {
                if (unitName.equals(environmentStock.getUnitName())) {
                    eDto.getDatas().set(eDto.getUnitNames().indexOf(unitName), environmentStock.getCount());
                    break;
                }
            }
        }
        return eDto;
    }

    public EnvironmentStockEchart getEnvrionmentAreaEventAll(EnvironmentEventQuery query) {
        EnvironmentStockEchart eDto = new EnvironmentStockEchart();
        eDto.setDatas(new ArrayList<>());
        eDto.setUnitNames(new ArrayList<>());
        List<EnvironmentStock> list = new ArrayList<>();
        if (query.getDescription().equals("week")) {
            list = baseMapper.getAreaEnvrionmentEventAllWeek();
        } else if (query.getDescription().equals("month")) {
            list = baseMapper.getAreaEnvrionmentEventAllMonth();
        } else if (query.getDescription().equals("year")) {
            list = baseMapper.getAreaEnvrionmentEventAllYear();
        }
        for (EnvironmentStock environmentStock : list) {
            eDto.getUnitNames().add(environmentStock.getUnitName());
            eDto.getDatas().add(environmentStock.getCount());
        }
        return eDto;
    }

    @Override
    public EnvironmentStockEchart getEnvironmentEventById(EnvironmentEventQuery query) {
        EnvironmentStockEchart eDto = new EnvironmentStockEchart();
        eDto.setDatas(new ArrayList<>());
        eDto.setUnitNames(new ArrayList<>());
        // LocalDate today = LocalDate.now();
        if (query.getDayType().equals("week")) {
            List<EnvironmentStock> list = baseMapper.getEnvironmentEventWeekById(query.getEnvironmentId());
            List<String> date = DatePickUtil.getWeekNowMMDD();
            for (String d : date) {
                Boolean isExit = false;
                for (EnvironmentStock environmentStock : list) {
                    if (d.contains(environmentStock.getTime())) {
                        eDto.getUnitNames().add(environmentStock.getTime());
                        eDto.getDatas().add(environmentStock.getCount());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    eDto.getUnitNames().add(d);
                    eDto.getDatas().add(0);
                }
            }

            // LocalDate startOfWeek = today.minusDays(6);
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // for (int i = 0; i < 7; i++) {
            // String date = startOfWeek.plusDays(i).format(formatter);
            // Boolean isExit = false;
            // for (EnvironmentStock environmentStock : list) {
            // if (date.contains(environmentStock.getTime())) {
            // eDto.getUnitNames().add(environmentStock.getTime());
            // eDto.getDatas().add(environmentStock.getCount());
            // isExit = true;
            // }
            // }
            // if (!isExit) {
            // eDto.getUnitNames().add(date);
            // eDto.getDatas().add(0);
            // }
            // }

        } else if (query.getDayType().equals("month")) {
            List<EnvironmentStock> list = baseMapper.getEnvironmentEventMonthById(query.getEnvironmentId());
            List<String> date = DatePickUtil.getYearNow();
            for (String d : date) {
                Boolean isExit = false;
                for (EnvironmentStock environmentStock : list) {
                    if (d.contains(environmentStock.getTime())) {
                        eDto.getUnitNames().add(environmentStock.getTime());
                        eDto.getDatas().add(environmentStock.getCount());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    eDto.getUnitNames().add(d);
                    eDto.getDatas().add(0);
                }
            }

            // LocalDate startOfMonth = LocalDate.of(today.getYear(), today.getMonth(), 1);
            // LocalDate oneMonthAgo = today.minusMonths(1);
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // for (LocalDate date = oneMonthAgo; !date.isAfter(today); date =
            // date.plusDays(1)) {
            // String dateStr = date.format(formatter);
            // eDto.getUnitNames().add(dateStr);
            // Boolean isExit = false;
            // for (EnvironmentStock environmentStock : list) {
            // if (dateStr.contains(environmentStock.getTime())) {
            // eDto.getDatas().add(environmentStock.getCount());
            // isExit = true;
            // }
            // }
            // if (!isExit) {
            // eDto.getDatas().add(0);
            // }
            // }

            // for (int i = 0; i < today.lengthOfMonth(); i++) {
            // String date = startOfMonth.plusDays(i).format(formatter);
            // Boolean isExit = false;
            // for (EnvironmentStock environmentStock : list) {
            // if (date.contains(environmentStock.getTime())) {
            // eDto.getUnitNames().add(environmentStock.getTime());
            // eDto.getDatas().add(environmentStock.getCount());
            // isExit = true;
            // }
            // }
            // if (!isExit) {
            // eDto.getUnitNames().add(date);
            // eDto.getDatas().add(0);
            // }
            // }

        } else if (query.getDayType().equals("year")) {
            List<EnvironmentStock> list = baseMapper.getEnvironmentEventYearById(query.getEnvironmentId());
            List<String> date = DatePickUtil.getYearNow();
            for (String d : date) {
                Boolean isExit = false;
                for (EnvironmentStock environmentStock : list) {
                    if (d.equals(environmentStock.getTime())) {
                        eDto.getUnitNames().add(environmentStock.getTime());
                        eDto.getDatas().add(environmentStock.getCount());
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    eDto.getUnitNames().add(d);
                    eDto.getDatas().add(0);
                }
            }

            // for (int i = 1; i <= 12; i++) {
            // Boolean isExit = false;
            // for (EnvironmentStock environmentStock : list) {
            // if (i == Integer.parseInt(environmentStock.getTime())) {
            // eDto.getUnitNames().add(environmentStock.getTime());
            // eDto.getDatas().add(environmentStock.getCount());
            // isExit = true;
            // }
            // }
            // if (!isExit) {
            // eDto.getUnitNames().add(String.valueOf(i));
            // eDto.getDatas().add(0);
            // }
            // }
        }
        return eDto;

    }

    public EventEchartOneDTO getAreaStatisticsTotal(StatisticsQuery query) {
        List<AreaStatisticsDTO> result = new ArrayList<>();
        EventEchartOneDTO eDto = new EventEchartOneDTO();
        eDto.setData(new ArrayList<>());
        eDto.setTimes(new ArrayList<>());
        if (query.getDayType().equals("week")) {
            result = baseMapper.getAreaStatisticsWeek();
        } else if (query.getDayType().equals("month")) {
            result = baseMapper.getAreaStatisticsMonth();
        } else if (query.getDayType().equals("year")) {
            result = baseMapper.getAreaStatisticsYear();
        }

        for (AreaStatisticsDTO a : result) {
            eDto.getTimes().add(a.getManufacturer());
            eDto.getData().add(a.getCount());
        }
        return eDto;
    }

    public List<AllEventEchartDTO> getAllEventEchart() {
        return baseMapper.getAllEventEchart();
    }

    public List<AllEventEchartDTO> getAllEquipmentAreaEchart() {
        return baseMapper.getAllEquipmentAreaEchart();
    }

    public List<AllEventEchartDTO> getAllEnvironmentAreaEchart() {
        return baseMapper.getAllEnvironmentAreaEchart();
    }

    public List<AllEventEchartDTO> getGongYiJieDianAreaEchart() {
        return baseMapper.getGongYiJieDianAreaEchart();
    }

    public DareaResultDTO getAreaStatisticsByDate(String startTime, String endTime) {
        List<DareaDTO> temperatureData = baseMapper.getAreaStatisticsByDate(startTime, endTime);
        for (DareaDTO item : temperatureData) {
            if (item.getArea() == null) {
                item.setArea("未知");
            }
        }
        // startTime 和endTime 是YYYY-MM-DD
        // 打印长度
        Set<String> timeSlotsSet = new TreeSet<>(); // 自动排序时间
        Set<String> areasSet = new HashSet<>();
        Map<String, Map<String, Double>> areaToTimeValueMap = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startTime, formatter);
        LocalDate end = LocalDate.parse(endTime, formatter);
        for (LocalDate date = start; date.compareTo(end) <= 0; date = date.plusDays(1)) {
            timeSlotsSet.add(date.format(formatter));
        }
        List<SeriesDTO> seriesList = new ArrayList<>();
        // 分组数据
        for (DareaDTO item : temperatureData) {
            String area = item.getArea();
            // String timeSlot = item.getTimeSlot();
            // Double value = item.getAvgValue();

            // areasSet.add(area);

            // areaToTimeValueMap
            // .computeIfAbsent(area, k -> new HashMap<>())
            // .put(timeSlot, value);

            SeriesDTO s = new SeriesDTO();
            s.setName(area);
            s.setData(new ArrayList<>());
            seriesList.add(s);
        }

        for (SeriesDTO s : seriesList) {
            for (String timeSlot : timeSlotsSet) {
                Boolean isExit = false;
                for (DareaDTO item : temperatureData) {
                    if (item.getArea().equals(s.getName()) && item.getTimeSlot().equals(timeSlot)) {
                        isExit = true;
                        s.getData().add(item.getAvgValue());
                        break;
                    }
                }
                if (!isExit) {
                    s.getData().add(0.0);
                }
            }
        }

        // 转为列表
        // List<String> xData = new ArrayList<>(timeSlotsSet);

        // for (String area : areasSet) {
        // SeriesDTO seriesItem = new SeriesDTO();
        // seriesItem.setName(area);

        // List<Double> dataList = new ArrayList<>();
        // Map<String, Double> timeMap = areaToTimeValueMap.get(area);

        // for (String timeSlot : xData) {
        // dataList.add(timeMap.getOrDefault(timeSlot, null));
        // }

        // seriesItem.setData(dataList);
        // seriesList.add(seriesItem);
        // }

        // 填充返回结构
        DareaResultDTO result = new DareaResultDTO();
        // 将timeSlotsSet转换为List<String>
        result.setXData(new ArrayList<>(timeSlotsSet));
        result.setSeries(seriesList);
        return result;
    }

    public List<AllEventEchartDTO> getGongYiJieDianTodayAlarmCount() {
        return baseMapper.getGongYiJieDianTodayAlarmCount();
    }

    public Integer getTodayAlarmCount() {
        return baseMapper.getTodayAlarmCount();
    }
}
