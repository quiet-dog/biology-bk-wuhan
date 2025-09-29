package com.biology.domain.manage.detection.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.detection.dto.DareaDTO;
import com.biology.domain.manage.detection.dto.DetectionAreaTypeDTO;
import com.biology.domain.manage.detection.dto.DetectionAreaTypeEchartDTO;
import com.biology.domain.manage.detection.dto.DetectionCountEchartTypeDTO;
import com.biology.domain.manage.detection.dto.DetectionStatisticsDTO;
import com.biology.domain.manage.detection.dto.PowerDTO;
import com.biology.domain.manage.detection.query.DetectionStockQuery;
import com.biology.domain.manage.detection.query.HistoryQuery;
import com.biology.domain.manage.detection.query.PowerQuery;

public interface DetectionService extends IService<DetectionEntity> {

    public List<DetectionStatisticsDTO> getStatistics(Long environmentId);

    public List<PowerDTO> getPowerStatic(PowerQuery query);

    public List<PowerDTO> getHistoryData(HistoryQuery query);

    public DetectionAreaTypeEchartDTO getDetectionCountTypeArea(DetectionStockQuery query);

    public DetectionCountEchartTypeDTO getHistoryPowersByType(PowerQuery type);

    public DetectionCountEchartTypeDTO getHistoryPowersByTypeTotal(PowerQuery query);

    public DetectionCountEchartTypeDTO getHistoryAreaByTypeTotal(PowerQuery query);

    public DetectionCountEchartTypeDTO getAareUnitNameHistory(PowerQuery query);

    public DetectionCountEchartTypeDTO getHistoryDayByEnvironmentId(PowerQuery query);

    public DetectionCountEchartTypeDTO getZuiXinShuJu(PowerQuery query);

    public Double getCurrentMonthPowerUsage(Long environmentId);

    public Double getCurrentMonthWaterUsage(Long environmentId);

    // 获取近7天的数据
    public List<PowerDTO> getElectricityByEnvironmentIdByWeek(Long environmentId);

    // 获取近30天的数据
    public List<PowerDTO> getElectricityByEnvironmentIdByMonth(Long environmentId);
    // 获取从当前月往前推1年的每个月的SUM

    public List<PowerDTO> getElectricityByEnvironmentIdByYear(Long environmentId);

    public List<PowerDTO> getWaterByEnvironmentIdByWeek(Long environmentId);

    public List<PowerDTO> getWaterByEnvironmentIdByMonth(Long environmentId);

    public List<PowerDTO> getWaterByEnvironmentIdByYear(Long environmentId);

    public List<DareaDTO> getTemperatureDataByAreaAndTimeSlot(String unitName, String beginTime, String endTime);

    public List<DareaDTO> getHistoryDataByEnvironmentId(
            String beginTime,
            Long environmentId);
}
