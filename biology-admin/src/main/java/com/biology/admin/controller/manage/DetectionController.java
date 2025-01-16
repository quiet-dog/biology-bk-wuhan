package com.biology.admin.controller.manage;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.detection.DetectionApplicationService;
import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.detection.dto.DetectionAreaTypeEchartDTO;
import com.biology.domain.manage.detection.dto.DetectionCountEchartTypeDTO;
import com.biology.domain.manage.detection.dto.DetectionDTO;
import com.biology.domain.manage.detection.dto.PowerDTO;
import com.biology.domain.manage.detection.dto.PowerEchartDTO;
import com.biology.domain.manage.detection.dto.StatisticsDetailDTO;
import com.biology.domain.manage.detection.query.DetectionQuery;
import com.biology.domain.manage.detection.query.DetectionStockQuery;
import com.biology.domain.manage.detection.query.HistoryQuery;
import com.biology.domain.manage.detection.query.PowerQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "环境监测API", description = "环境监测API的增删查改")
@RestController
@RequestMapping("/manage/detection")
@Validated
@RequiredArgsConstructor
public class DetectionController extends BaseController {
    private final DetectionApplicationService detectionApplicationService;

    @Operation(summary = "添加环境数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddDetectionCommand command) {
        detectionApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取环境数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<DetectionDTO>> list(DetectionQuery query) {
        return ResponseDTO.ok(detectionApplicationService.list(query));
    }

    @Operation(summary = "获取环境监测信息统计")
    @GetMapping("/data/{detectionId}")
    public ResponseDTO<StatisticsDetailDTO> getStatistics(
            @PathVariable(value = "detectionId", required = false) Long detectionId) {
        return ResponseDTO.ok(detectionApplicationService.getStatistics(detectionId));
    }

    @Operation(summary = "获取环境监测功率统计")
    @GetMapping("/powerStatic")
    public ResponseDTO<PowerEchartDTO> getPowerStatic(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getPowerStatic(query));
    }

    @Operation(summary = "获取环境监测功率统计")
    @GetMapping("/historyStatic")
    public ResponseDTO<PowerEchartDTO> getHistoryData(HistoryQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getHistoryData(query));
    }

    @Operation(summary = "数据大屏获取不同类型环境档案的当天功耗趋势")
    @GetMapping("/powerByType")
    public ResponseDTO<DetectionCountEchartTypeDTO> getHistoryPowersByType(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getHistoryPowersByType(query));
    }

    @Operation(summary = "数据大屏获取水电不同类型环境档案的周月年总功耗")
    @GetMapping("/powerByTypeTotal")
    public ResponseDTO<DetectionCountEchartTypeDTO> getHistoryPowersByTypeTotal(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getHistoryPowersByTypeTotal(query));
    }

    @Operation(summary = "数据大屏获取水电不同类型环境档案的区域的周月年")
    @GetMapping("/powerByAreaTotal")
    public ResponseDTO<DetectionCountEchartTypeDTO> getHistoryAreaByTypeTotal(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getHistoryAreaByTypeTotal(query));
    }

    @Operation(summary = "首页获取环境监测指标")
    @GetMapping("/unitNameAndArea")
    public ResponseDTO<DetectionCountEchartTypeDTO> getAareUnitNameHistory(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getAareUnitNameHistory(query));
    }

    @Operation(summary = "数据大屏获取环境监测指标")
    @GetMapping("/environmentDay")
    public DetectionCountEchartTypeDTO getHistoryDayByEnvironmentId(PowerQuery query) {
        return detectionApplicationService.getHistoryDayByEnvironmentId(query);
    }

    @Operation(summary = "数据大屏获取环境监测指标")
    @GetMapping("/getZuiXinShuJu")
    public ResponseDTO<DetectionCountEchartTypeDTO> getZuiXinShuJu(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getZuiXinShuJu(query));
    }
}
