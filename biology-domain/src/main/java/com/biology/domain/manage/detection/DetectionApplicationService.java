package com.biology.domain.manage.detection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.detection.db.DetectionEntity;
import com.biology.domain.manage.detection.db.DetectionService;
import com.biology.domain.manage.detection.dto.DetectionAreaTypeEchartDTO;
import com.biology.domain.manage.detection.dto.DetectionCountEchartTypeDTO;
import com.biology.domain.manage.detection.dto.DetectionDTO;
import com.biology.domain.manage.detection.dto.DetectionStatisticsDTO;
import com.biology.domain.manage.detection.dto.NengHaoDTO;
import com.biology.domain.manage.detection.dto.PowerDTO;
import com.biology.domain.manage.detection.dto.PowerEchartDTO;
import com.biology.domain.manage.detection.dto.StatisticsDetailDTO;
import com.biology.domain.manage.detection.model.DetectionFactory;
import com.biology.domain.manage.detection.model.DetectionModel;
import com.biology.domain.manage.detection.query.DetectionQuery;
import com.biology.domain.manage.detection.query.DetectionStockQuery;
import com.biology.domain.manage.detection.query.HistoryQuery;
import com.biology.domain.manage.detection.query.PowerQuery;
import com.biology.domain.manage.detection.query.SearchNengHaoQuery;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;
import com.biology.domain.manage.environment.dto.EnvironmentStatisticsDTO;
import com.biology.domain.manage.event.db.EventEntity;
import com.biology.domain.manage.event.dto.EventDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetectionApplicationService {
    private final DetectionFactory detectionFactory;

    private final DetectionService detectionService;

    public void create(AddDetectionCommand command) {
        DetectionModel detectionModel = detectionFactory.create();
        detectionModel.loadAddDetectionCommand(command);
        detectionModel.insert();
    }

    public DetectionModel addDetection(AddDetectionCommand command) {
        DetectionModel detectionModel = detectionFactory.create();
        detectionModel.loadAddDetectionCommand(command);
        detectionModel.insert();
        return detectionModel;
    }

    public PageDTO<DetectionDTO> list(DetectionQuery query) {
        Page<DetectionEntity> page = detectionService.page(query.toPage(), query.toQueryWrapper());
        List<DetectionDTO> records = page.getRecords().stream().map(DetectionDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public StatisticsDetailDTO getStatistics(Long environmentId) {
        List<DetectionStatisticsDTO> list = detectionService.getStatistics(environmentId);
        StatisticsDetailDTO statisticsDetailDTO = new StatisticsDetailDTO(environmentId, list);
        return statisticsDetailDTO;
    }

    public PowerEchartDTO getPowerStatic(PowerQuery query) {
        PowerEchartDTO powerEchartDTO = new PowerEchartDTO();
        powerEchartDTO.setData(new ArrayList<>());
        powerEchartDTO.setTime(new ArrayList<>());
        List<PowerDTO> powerData = detectionService.getPowerStatic(query);
        for (PowerDTO powerDTO : powerData) {
            powerEchartDTO.getData().add(powerDTO.getData());
            powerEchartDTO.getTime().add(powerDTO.getTime());
        }
        return powerEchartDTO;
    }

    public PowerEchartDTO getHistoryData(HistoryQuery query) {
        PowerEchartDTO powerEchartDTO = new PowerEchartDTO();
        powerEchartDTO.setData(new ArrayList<>());
        powerEchartDTO.setTime(new ArrayList<>());

        List<PowerDTO> powerData = detectionService.getHistoryData(query);
        for (PowerDTO powerDTO : powerData) {
            powerEchartDTO.getData().add(powerDTO.getData());
            powerEchartDTO.getTime().add(powerDTO.getTime());
        }
        return powerEchartDTO;
    }

    public DetectionAreaTypeEchartDTO getDetectionCountTypeArea(DetectionStockQuery query) {
        return detectionService.getDetectionCountTypeArea(query);
    }

    public DetectionCountEchartTypeDTO getHistoryPowersByType(PowerQuery query) {
        return detectionService.getHistoryPowersByType(query);
    }

    public DetectionCountEchartTypeDTO getHistoryPowersByTypeTotal(PowerQuery query) {
        return detectionService.getHistoryPowersByTypeTotal(query);
    }

    public DetectionCountEchartTypeDTO getHistoryAreaByTypeTotal(PowerQuery query) {
        return detectionService.getHistoryAreaByTypeTotal(query);
    }

    public DetectionCountEchartTypeDTO getAareUnitNameHistory(PowerQuery query) {
        return detectionService.getAareUnitNameHistory(query);
    }

    public DetectionCountEchartTypeDTO getHistoryDayByEnvironmentId(PowerQuery query) {
        return detectionService.getHistoryDayByEnvironmentId(query);
    }

    public DetectionCountEchartTypeDTO getZuiXinShuJu(PowerQuery query) {
        return detectionService.getZuiXinShuJu(query);
    }

    public PageDTO<NengHaoDTO> getNengHaoList(SearchNengHaoQuery query) {
        Page<DetectionEntity> page = detectionService.page(query.toPage(), query.toQueryWrapper());
        List<DetectionDTO> records = page.getRecords().stream().map(DetectionDTO::new).collect(Collectors.toList());
        List<NengHaoDTO> list = new ArrayList<>();
        for (DetectionDTO entity : records) {
            NengHaoDTO nengHaoDTO = new NengHaoDTO(entity);
            if (nengHaoDTO.getElectricityValue() != 0) {
                nengHaoDTO.setTotalValue(detectionService.getCurrentMonthPowerUsage(nengHaoDTO.getEnvironmentId()));
            }
            if (nengHaoDTO.getWaterValue() != 0) {
                nengHaoDTO.setTotalValue(detectionService.getCurrentMonthWaterUsage(nengHaoDTO.getEnvironmentId()));
            }
        }
        return new PageDTO<>(list, page.getTotal());
    }

}
