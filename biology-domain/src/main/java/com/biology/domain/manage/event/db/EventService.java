package com.biology.domain.manage.event.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.detection.dto.DareaDTO;
import com.biology.domain.manage.detection.dto.DareaResultDTO;
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

public interface EventService extends IService<EventEntity> {

    public List<WeekStatisticsDTO> getWeekStatistics(String dayType);

    public List<AreaStatisticsDTO> getAreaStatistics(AreaStatisticsQuery query);

    public EventTotalDTO getEventTotal();

    public HistoryEventEchart getHistoryEventAll(StatisticsQuery query);

    public EnvironmentStockEchart getEnvrionmentEventAllWeek(EnvironmentEventQuery query);

    public EnvironmentStockEchart getEnvrionmentAreaEventAll(EnvironmentEventQuery query);

    public EnvironmentStockEchart getEnvironmentEventById(EnvironmentEventQuery query);

    EventEchartOneDTO getAreaStatisticsTotal(StatisticsQuery query);

    public List<AllEventEchartDTO> getAllEventEchart();

    public List<AllEventEchartDTO> getAllEquipmentAreaEchart();

    public List<AllEventEchartDTO> getAllEnvironmentAreaEchart();

    public DareaResultDTO getAreaStatisticsByDate(String startTime, String endTime);
}
