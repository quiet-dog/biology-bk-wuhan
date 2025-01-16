package com.biology.domain.manage.detection.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;
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
}
