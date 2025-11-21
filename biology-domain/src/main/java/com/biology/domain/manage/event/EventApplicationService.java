package com.biology.domain.manage.event;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailService;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelService;
import com.biology.domain.manage.alarmlevel.dto.EnvironmentAlarmInfoDTO;
import com.biology.domain.manage.detection.dto.DareaResultDTO;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.db.EnvironmentService;
import com.biology.domain.manage.event.command.AddEventCommand;
import com.biology.domain.manage.event.command.UpdateEventCommand;
import com.biology.domain.manage.event.db.EventEntity;
import com.biology.domain.manage.event.db.EventService;
import com.biology.domain.manage.event.dto.AllEventEchartDTO;
import com.biology.domain.manage.event.dto.AreaStatisticsDTO;
import com.biology.domain.manage.event.dto.EnvironmentStockEchart;
import com.biology.domain.manage.event.dto.EventDTO;
import com.biology.domain.manage.event.dto.EventDeviceNameDTO;
import com.biology.domain.manage.event.dto.EventEchartDTO;
import com.biology.domain.manage.event.dto.EventEchartOneDTO;
import com.biology.domain.manage.event.dto.EventTotalDTO;
import com.biology.domain.manage.event.dto.HistoryEventEchart;
import com.biology.domain.manage.event.dto.StatisticsDTO;
import com.biology.domain.manage.event.dto.TypeDTO;
import com.biology.domain.manage.event.dto.WeekStatisticsDTO;
import com.biology.domain.manage.event.model.EventModel;
import com.biology.domain.manage.event.query.AreaStatisticsQuery;
import com.biology.domain.manage.event.query.EnvironmentEventQuery;
import com.biology.domain.manage.event.query.EventSearch;
import com.biology.domain.manage.event.query.StatisticsQuery;
import com.biology.domain.manage.materials.db.MaterialsValueEntity;
import com.biology.domain.manage.materials.db.MaterialsValueService;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueService;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJian.dto.XunJianDTO;
import com.biology.domain.manage.xunJianHistory.db.XunJianEventEntity;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;

import cn.hutool.core.date.DateUtil;

import com.biology.domain.manage.event.model.EventFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventApplicationService {

    private final EventFactory eventFactory;

    private final EventService eventService;

    private final ThresholdValueService thresholdValueService;

    private final MaterialsValueService materialsValueService;

    private final EnvironmentService environmentService;

    private final AlarmlevelDetailService alarmlevelDetailService;

    private final AlarmlevelService alarmlevelService;

    public void createEvent(AddEventCommand command) {
        EventModel eventModel = eventFactory.create();
        eventModel.loadAddEventCommand(command);
        eventModel.insert();

        checkXunJian(eventModel);
    }

    public void updateEvent(UpdateEventCommand command) {
        EventModel eventModel = eventFactory.loadById(command.getEventId());
        eventModel.loadUpdateEventCommand(command);
        eventModel.updateById();
    }

    public void deleteEmergencies(List<Long> eventIds) {
        for (Long eventId : eventIds) {
            EventModel eventModel = eventFactory.loadById(eventId);
            eventModel.deleteById();
        }
    }

    public EventDTO getEventInfo(Long eventId) {
        EventEntity eventEntity = eventService.getById(eventId);

        return new EventDTO(eventEntity);
    }

    public PageDTO<EventDTO> getEventList(EventSearch query) {
        Page<EventEntity> page = eventService.page(query.toPage(), query.toQueryWrapper());
        List<EventDTO> records = page.getRecords().stream().map(EventDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public PageDTO<EventDeviceNameDTO> getEventDeviceNameList(EventSearch query) {
        Page<EventEntity> page = eventService.page(query.toPage(), query.toQueryWrapper());
        List<EventDeviceNameDTO> records = page.getRecords().stream().map(EventDeviceNameDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public ResponseDTO<List<String>> getTypeList() {
        List<String> thresholdValueEntitiesList = thresholdValueService.getTypeList();
        List<String> materialsValueEntitiesList = materialsValueService.selectTypes();

        TypeDTO typeList = new TypeDTO();
        typeList.setThresholdTypes(thresholdValueEntitiesList);
        typeList.setMaterialsTypes(materialsValueEntitiesList);

        List<String> types = new ArrayList<>();
        for (String type : thresholdValueEntitiesList) {
            boolean isExit = false;
            for (String type1 : types) {
                if (type.equals(type1)) {
                    isExit = true;
                }
            }
            if (!isExit) {
                types.add(type);
            }
        }
        for (String type : materialsValueEntitiesList) {
            boolean isExit = false;
            for (String type1 : types) {
                if (type.equals(type1)) {
                    isExit = true;
                }
            }
            if (!isExit) {
                types.add(type);
            }
        }
        return ResponseDTO.ok(types);
    }

    public AlarmlevelDetailEntity checkEnvironment(EnvironmentAlarmInfoDTO equAlarmInfoDTO) {
        QueryWrapper<EnvironmentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("environment_id", equAlarmInfoDTO.getEnvironmentId());
        EnvironmentEntity environmentEntity = environmentService.getOne(queryWrapper);
        if (environmentEntity == null) {
            return null;
        }

        // QueryWrapper<AlarmlevelEntity> queryWrapper1 = new QueryWrapper<>();
        // queryWrapper1.eq("environment_id", environmentEntity.getEnvironmentId())
        // .eq("unit", equAlarmInfoDTO.getUnit());
        // AlarmlevelEntity alarmlevelEntity = alarmlevelService.getOne(queryWrapper1);
        // if (alarmlevelEntity == null) {
        // return null;
        // }

        QueryWrapper<AlarmlevelDetailEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("environment_id", environmentEntity.getEnvironmentId());
        List<AlarmlevelDetailEntity> alarmlevelDetailList = alarmlevelDetailService.getBaseMapper()
                .selectList(queryWrapper2);
        if (alarmlevelDetailList.size() == 0) {
            return null;
        }

        for (AlarmlevelDetailEntity alarmlevelDetailEntity : alarmlevelDetailList) {
            if (!Double.isNaN(alarmlevelDetailEntity.getMax()) && !Double.isNaN(alarmlevelDetailEntity.getMin())) {
                if (alarmlevelDetailEntity.getMin() < equAlarmInfoDTO.getValue()
                        && equAlarmInfoDTO.getValue() < alarmlevelDetailEntity.getMax()) {
                    QueryWrapper<EventEntity> queryWrapper3 = new QueryWrapper<>();
                    queryWrapper3
                            .eq("environment_id", equAlarmInfoDTO.getEnvironmentId())
                            .eq("level", alarmlevelDetailEntity.getLevel())
                            .ge("create_time", DateUtil.offsetMinute(new Date(), -30));
                    // 一分钟内相同环境不重复报警
                    // queryWrapper3;
                    EventEntity eventEntity = new EventEntity().selectOne(queryWrapper3);
                    if (eventEntity != null) {
                        return null;
                    }
                    return alarmlevelDetailEntity;
                }
            }

            if (!Double.isNaN(alarmlevelDetailEntity.getMin()) && Double.isNaN(alarmlevelDetailEntity.getMax())
                    && alarmlevelDetailEntity.getMin() < equAlarmInfoDTO.getValue()) {

                QueryWrapper<EventEntity> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3
                        .eq("environment_id", equAlarmInfoDTO.getEnvironmentId())
                        .eq("level", alarmlevelDetailEntity.getLevel())
                        .ge("create_time", DateUtil.offsetMinute(new Date(), -30));
                EventEntity eventEntity = new EventEntity().selectOne(queryWrapper3);
                if (eventEntity != null) {
                    return null;
                }
                return alarmlevelDetailEntity;
            }

            if (!Double.isNaN(alarmlevelDetailEntity.getMax()) && Double.isNaN(alarmlevelDetailEntity.getMin())
                    && equAlarmInfoDTO.getValue() < alarmlevelDetailEntity.getMax()) {
                QueryWrapper<EventEntity> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3
                        .eq("environment_id", equAlarmInfoDTO.getEnvironmentId())
                        .eq("level", alarmlevelDetailEntity.getLevel())
                        .ge("create_time", DateUtil.offsetMinute(new Date(), -30));
                EventEntity eventEntity = new EventEntity().selectOne(queryWrapper3);
                if (eventEntity != null) {
                    return null;
                }
                return alarmlevelDetailEntity;
            }
        }

        return null;
    }

    // 获取各种报警类型的最近7天的每天的统计
    public List<EventEchartDTO> getWeekStatistics(String dayType) {
        List<WeekStatisticsDTO> listDTO = eventService.getWeekStatistics(dayType);
        List<EventEchartDTO> eventEchartList = new ArrayList<>();
        eventEchartList.add(new EventEchartDTO() {
            {
                setType("环境报警");
                setData(new ArrayList<>());
                setTimes(new ArrayList<>());
            }
        });
        eventEchartList.add(new EventEchartDTO() {
            {
                setType("物料报警");
                setData(new ArrayList<>());
                setTimes(new ArrayList<>());

            }
        });
        eventEchartList.add(new EventEchartDTO() {
            {
                setType("设备报警");
                setData(new ArrayList<>());
                setTimes(new ArrayList<>());
                setRate(new ArrayList<>());

            }
        });

        eventEchartList.add(new EventEchartDTO() {
            {
                setType("工艺节点报警");
                setData(new ArrayList<>());
                setTimes(new ArrayList<>());

            }
        });

        LocalDate today = LocalDate.now();

        if (dayType == null || dayType.equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = today.format(formatter);
            for (EventEchartDTO eventEchartDTO : eventEchartList) {
                eventEchartDTO.getTimes().add(date);
            }
            for (EventEchartDTO eventEchartDTO : eventEchartList) {
                boolean isExit = false;
                for (WeekStatisticsDTO weekStatisticsDTO : listDTO) {
                    if (weekStatisticsDTO.getDataTime() != null && weekStatisticsDTO.getDataTime().equals(date)) {
                        eventEchartDTO.getData().add(weekStatisticsDTO.getCount());
                        isExit = true;
                    }
                }

                if (!isExit) {
                    eventEchartDTO.getData().add(0.0);
                }
            }
        }

        // 获取本周的开始日期（假设周一为一周的开始）

        // 输出本周的 7 天日期

        if (dayType.equals("week")) {
            for (EventEchartDTO e : eventEchartList) {
                e.setTimes(DatePickUtil.getWeekNowMMDD());
            }

            for (EventEchartDTO eventEchartDTO : eventEchartList) {
                for (String date : eventEchartDTO.getTimes()) {
                    boolean isExit = false;
                    for (WeekStatisticsDTO weekStatisticsDTO : listDTO) {
                        if (weekStatisticsDTO.getType().equals(eventEchartDTO.getType())
                                && weekStatisticsDTO.getDataTime() != null
                                && weekStatisticsDTO.getDataTime().contains(date)) {
                            if (weekStatisticsDTO.getType().equals("设备报警")) {
                                eventEchartDTO.getRate().add(weekStatisticsDTO.getRate());
                            }
                            eventEchartDTO.getData().add(weekStatisticsDTO.getCount());
                            isExit = true;
                        }
                    }
                    if (!isExit) {
                        if (eventEchartDTO.getType().equals("设备报警")) {
                            eventEchartDTO.getRate().add(0.0);
                        }
                        eventEchartDTO.getData().add(0.0);
                    }
                }
            }

            // LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 当前时间
            // for (int i = 0; i < 7; i++) {
            // String date = startOfWeek.plusDays(i).format(formatter);
            // for (EventEchartDTO eventEchartDTO : eventEchartList) {
            // eventEchartDTO.getTimes().add(date);
            // }
            // for (EventEchartDTO eventEchartDTO : eventEchartList) {
            // boolean isExit = false;
            // for (WeekStatisticsDTO weekStatisticsDTO : listDTO) {
            // if (weekStatisticsDTO.getType().equals(eventEchartDTO.getType())
            // && weekStatisticsDTO.getDataTime() != null
            // && weekStatisticsDTO.getDataTime().equals(date)) {
            // isExit = true;
            // eventEchartDTO.getData().add(weekStatisticsDTO.getCount());
            // }
            // }

            // if (!isExit) {
            // eventEchartDTO.getData().add(0.0);
            // }
            // }

            // }

        }
        if (dayType.equals("month")) {
            // listDTO = eventService.getMonthStatistics(dayType);
            for (EventEchartDTO e : eventEchartList) {
                e.setTimes(DatePickUtil.getMonthNowMMDD());
            }

            for (EventEchartDTO eventEchartDTO : eventEchartList) {
                for (String date : eventEchartDTO.getTimes()) {
                    boolean isExit = false;
                    for (WeekStatisticsDTO weekStatisticsDTO : listDTO) {
                        if (weekStatisticsDTO.getType().equals(eventEchartDTO.getType())
                                && weekStatisticsDTO.getDataTime() != null
                                && weekStatisticsDTO.getDataTime().contains(date)) {
                            if (eventEchartDTO.getType().equals("设备报警")) {
                                eventEchartDTO.getRate().add(weekStatisticsDTO.getRate());
                            }
                            eventEchartDTO.getData().add(weekStatisticsDTO.getCount());
                            isExit = true;
                        }
                    }
                    if (!isExit) {
                        if (eventEchartDTO.getType().equals("设备报警")) {
                            eventEchartDTO.getRate().add(0.0);
                        }
                        eventEchartDTO.getData().add(0.0);
                    }
                }
            }

            // 当前月份每天的统计
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 获取当前月份的第一天
            // LocalDate firstDay = today.withDayOfMonth(1);
            // 当前时间
            // for (int i = 0; i < firstDay.lengthOfMonth(); i++) {
            // String date = firstDay.plusDays(i).format(formatter);
            // for (EventEchartDTO eventEchartDTO : eventEchartList) {
            // eventEchartDTO.getTimes().add(date);
            // }
            // for (EventEchartDTO eventEchartDTO : eventEchartList) {
            // eventEchartDTO.setEventDate(date);
            // boolean isExit = false;
            // for (WeekStatisticsDTO weekStatisticsDTO : listDTO) {
            // if (weekStatisticsDTO.getType().equals(eventEchartDTO.getType())
            // && weekStatisticsDTO.getDataTime() != null
            // && weekStatisticsDTO.getDataTime().equals(date)) {
            // eventEchartDTO.getData().add(weekStatisticsDTO.getCount());
            // isExit = true;
            // }
            // }
            // if (!isExit) {
            // eventEchartDTO.getData().add(0.0);
            // }
            // }
            // }

            // LocalDate oneMonthAgo = today.minusMonths(1);
            // // 定义日期格式
            // for (LocalDate date = oneMonthAgo; !date.isAfter(today); date =
            // date.plusDays(1)) {
            // String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // for (EventEchartDTO eventEchartDTO : eventEchartList) {
            // eventEchartDTO.getTimes().add(dateStr);
            // }
            // for (EventEchartDTO eventEchartDTO : eventEchartList) {
            // eventEchartDTO.setEventDate(dateStr);
            // boolean isExit = false;
            // for (WeekStatisticsDTO weekStatisticsDTO : listDTO) {
            // if (weekStatisticsDTO.getType().equals(eventEchartDTO.getType())
            // && weekStatisticsDTO.getDataTime() != null
            // && weekStatisticsDTO.getDataTime().equals(dateStr)) {
            // eventEchartDTO.getData().add(weekStatisticsDTO.getCount());
            // isExit = true;
            // }
            // }
            // if (!isExit) {
            // eventEchartDTO.getData().add(0.0);
            // }
            // }
            // }

        }
        if (dayType.equals("year")) {
            for (EventEchartDTO e : eventEchartList) {
                // e.setTimes(DatePickUtil.getYearNow());
                e.setTimes(DatePickUtil.getYearNowBefore());
            }

            for (EventEchartDTO eventEchartDTO : eventEchartList) {
                for (String date : eventEchartDTO.getTimes()) {
                    boolean isExit = false;
                    for (WeekStatisticsDTO weekStatisticsDTO : listDTO) {
                        if (weekStatisticsDTO.getType().equals(eventEchartDTO.getType())
                                && weekStatisticsDTO.getDataTime() != null
                                && weekStatisticsDTO.getDataTime().equals(date)) {
                            if (eventEchartDTO.getType().equals("设备报警")) {
                                eventEchartDTO.getRate().add(weekStatisticsDTO.getRate());
                            }
                            eventEchartDTO.getData().add(weekStatisticsDTO.getCount());
                            isExit = true;
                        }
                    }
                    if (!isExit) {
                        if (eventEchartDTO.getType().equals("设备报警")) {
                            eventEchartDTO.getRate().add(0.0);
                        }
                        eventEchartDTO.getData().add(0.0);
                    }
                }
            }

            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

            // // 今年每个月的统计
            // for (int i = 1; i <= 12; i++) {
            // String date = today.withMonth(i).format(formatter);
            // for (EventEchartDTO eventEchartDTO : eventEchartList) {
            // eventEchartDTO.getTimes().add(String.valueOf(i));
            // }
            // for (EventEchartDTO eventEchartDTO : eventEchartList) {
            // eventEchartDTO.setEventDate(date);
            // boolean isExit = false;
            // for (WeekStatisticsDTO weekStatisticsDTO : listDTO) {
            // if (weekStatisticsDTO.getType().equals(eventEchartDTO.getType())
            // && weekStatisticsDTO.getDataTime() != null
            // && weekStatisticsDTO.getDataTime().equals(date)) {
            // isExit = true;
            // eventEchartDTO.getData().add(weekStatisticsDTO.getCount());
            // }
            // }
            // if (!isExit) {
            // eventEchartDTO.getData().add(0.0);
            // }
            // }
            // }

        }

        return eventEchartList;
    }

    public List<AreaStatisticsDTO> getAreaStatistics(AreaStatisticsQuery query) {
        return eventService.getAreaStatistics(query);
    }

    public EventTotalDTO getEventTotal() {
        return eventService.getEventTotal();
    }

    public HistoryEventEchart getHistoryEventAll(StatisticsQuery query) {
        return eventService.getHistoryEventAll(query);
    }

    public EnvironmentStockEchart getEnvrionmentEventAllWeek(EnvironmentEventQuery query) {
        return eventService.getEnvrionmentEventAllWeek(query);
    }

    public EnvironmentStockEchart getEnvrionmentAreaEventAll(EnvironmentEventQuery query) {
        return eventService.getEnvrionmentAreaEventAll(query);
    }

    public EnvironmentStockEchart getEnvironmentEventById(EnvironmentEventQuery query) {
        return eventService.getEnvironmentEventById(query);
    }

    public EventEchartOneDTO getAreaStatisticsTotal(StatisticsQuery query) {
        return eventService.getAreaStatisticsTotal(query);
    }

    public List<AllEventEchartDTO> getAllEventEchart() {
        return eventService.getAllEventEchart();
    }

    public List<AllEventEchartDTO> getAllEquipmentAreaEchart() {
        return eventService.getAllEquipmentAreaEchart();
    }

    public List<AllEventEchartDTO> getAllEnvironmentAreaEchart() {
        return eventService.getAllEnvironmentAreaEchart();
    }

    public List<AllEventEchartDTO> getGongYiJieDianAreaEchart() {
        return eventService.getGongYiJieDianAreaEchart();
    }

    public DareaResultDTO getAreaStatisticsByDate(String startTime, String endTime) {
        return eventService.getAreaStatisticsByDate(startTime, endTime);
    }

    // 统计工艺节点今日报警数量
    public Map<String, Integer> getGongYiJieDianTodayAlarmCount() {
        Map<String, Integer> result = new HashMap<>();
        List<AllEventEchartDTO> list = eventService.getGongYiJieDianTodayAlarmCount();
        result.put("轻微", 0);
        result.put("一般", 0);
        result.put("中度", 0);
        result.put("重要", 0);
        result.put("紧急", 0);
        for (AllEventEchartDTO allEventEchartDTO : list) {
            result.put(allEventEchartDTO.getName(), allEventEchartDTO.getValue());
        }
        return result;
    }

    public Map<String, Object> getTodatAlarmCount() {
        Map<String, Object> result = new HashMap<>();
        Integer num = eventService.getTodayAlarmCount();
        result.put("num", num);
        return result;
    }

    public void checkXunJian(EventModel event) {
        if (event == null) {
            return;
        }

        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int day = LocalDate.now().getDayOfMonth();
        int totalSeconds = hour * 3600 + minute * 60 + second;
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        String redisId = "";
        if (event.getEquipmentId() != null && event.getEquipmentId() > 0) {
            redisId = "equipment-" + event.getEquipmentId().toString();
        }

        if (event.getEnvironmentId() != null && event.getEnvironmentId() > 0) {
            redisId = "environment-" + event.getEnvironmentId().toString();
        }

        List<XunJianDTO> xunJianDTOs = CacheCenter.xunJianDeviceCache.getObjectById(redisId);
        if (xunJianDTOs != null) {
            for (XunJianDTO x : xunJianDTOs) {
                Boolean isExit = false;
                if (x.getXunJianLeiXing() != null && x.getXunJianPinLu() != null) {
                    if (x.getXunJianLeiXing().equals("定点巡检")) {
                        if (x.getXunJianPinLu().equals("每日")) {
                            isExit = (x.getTimeRange().get(0) == totalSeconds);
                        }
                        if (x.getXunJianPinLu().equals("每周")) {
                            isExit = (x.getTimeRange().get(0) == totalSeconds
                                    && x.getDayRange().contains(dayOfWeek - 1));
                        }
                        if (x.getXunJianPinLu().equals("每月")) {
                            isExit = (x.getTimeRange().get(0) == totalSeconds && x.getDayRange().contains(day - 1));
                        }
                    } else {
                        Boolean timeIsExit = false;
                        timeIsExit = (x.getTimeRange().get(0) < totalSeconds && totalSeconds < x.getTimeRange().get(1));
                        if (!timeIsExit) {
                            return;
                        }
                        if (x.getXunJianPinLu().equals("每日")) {
                            isExit = true;
                        }
                        if (x.getXunJianPinLu().equals("每周")) {
                            isExit = x.getDayRange().contains(dayOfWeek - 1);
                        }
                        if (x.getXunJianPinLu().equals("每月")) {
                            isExit = x.getDayRange().contains(day - 1);
                        }
                    }
                }

                if (isExit) {
                    QueryWrapper<XunJianHistoryEntity> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("xun_jian_id", x.getXunJianId())
                            .eq("status", "巡检中");
                    XunJianHistoryEntity xunJianHistoryEntity = new XunJianHistoryEntity().selectOne(queryWrapper);
                    if (xunJianHistoryEntity == null) {
                        xunJianHistoryEntity = new XunJianHistoryEntity();
                        xunJianHistoryEntity.setDayRange(x.getDayRange());
                        xunJianHistoryEntity.setFanWei(x.getFanWei());
                        xunJianHistoryEntity.setStatus("巡检中");
                        xunJianHistoryEntity.setTimeRange(x.getTimeRange());
                        xunJianHistoryEntity.setXunJianId(x.getXunJianId());
                        xunJianHistoryEntity.setXunJianLeiXing(x.getXunJianLeiXing());
                        xunJianHistoryEntity.setXunJianPinLu(x.getXunJianPinLu());
                        xunJianHistoryEntity.insert();
                    }

                    long timestamp13 = System.currentTimeMillis();

                    XunJianEventEntity xunJianEventEntity = new XunJianEventEntity();
                    xunJianEventEntity.setEventId(event.getEventId());
                    xunJianEventEntity.setXunJianHistoryId(xunJianHistoryEntity.getXunJianHistoryId());
                    xunJianEventEntity.setNowTime(timestamp13);
                    xunJianEventEntity.insert();
                }

            }
        }
    }
}
