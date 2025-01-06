package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biology.admin.customize.aop.accessLog.AccessLog;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.enums.common.BusinessTypeEnum;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.detection.DetectionApplicationService;
import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;
import com.biology.domain.manage.environment.query.EnvironmentQuery;
import com.biology.domain.manage.event.EventApplicationService;
import com.biology.domain.manage.event.command.AddEventCommand;
import com.biology.domain.manage.event.command.UpdateEventCommand;
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
import com.biology.domain.manage.event.query.AreaStatisticsQuery;
import com.biology.domain.manage.event.query.EnvironmentEventQuery;
import com.biology.domain.manage.event.query.EventSearch;
import com.biology.domain.manage.event.query.StatisticsQuery;
import com.biology.domain.manage.threshold.ThresholdApplicationService;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueService;
import com.biology.domain.manage.websocket.dto.DeviceDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "报警事件API", description = "报警事件的增删查改")
@RestController
@RequestMapping("/manage/event")
@Validated
@RequiredArgsConstructor
public class EventController extends BaseController {

    private final EventApplicationService eventApplicationService;

    private final ThresholdValueService thresholdValueService;

    private final ThresholdApplicationService thresholdApplicationService;

    private final DetectionApplicationService detectionApplicationService;

    @Operation(summary = "添加报警事件")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEventCommand command) {
        eventApplicationService.createEvent(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新报警事件")
    @PutMapping("/{eventId}")
    public ResponseDTO<Void> update(@PathVariable Long eventId, @RequestBody UpdateEventCommand command) {
        command.setEventId(eventId);
        eventApplicationService.updateEvent(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除报警事件")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> eventIds) {
        eventApplicationService.deleteEmergencies(eventIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取报警事件详情")
    @GetMapping("/{eventId}")
    public ResponseDTO<EventDTO> getEventInfo(
            @PathVariable(value = "eventId", required = false) Long eventId) {
        EventDTO eventDetailDTO = eventApplicationService.getEventInfo(eventId);
        return ResponseDTO.ok(eventDetailDTO);
    }

    @Operation(summary = "获取报警事件列表")
    @GetMapping
    public ResponseDTO<PageDTO<EventDTO>> list(EventSearch query) {
        PageDTO<EventDTO> list = eventApplicationService.getEventList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "报警事件列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, EventSearch query) {
        PageDTO<EventDTO> userList = eventApplicationService.getEventList(query);
        CustomExcelUtil.writeToResponse(userList.getRows(), EventDTO.class, response);
    }

    @Operation(summary = "报警事件列表导出")
    @GetMapping("/excelDeviceName")
    public void exportDeviceNameByExcel(HttpServletResponse response, EventSearch query) {
        PageDTO<EventDeviceNameDTO> userList = eventApplicationService.getEventDeviceNameList(query);
        CustomExcelUtil.writeToResponse(userList.getRows(), EventDeviceNameDTO.class, response);
    }

    @Operation(summary = "报警事件所有类型")
    @GetMapping("/types")
    public ResponseDTO<List<String>> getTypeList() {
        return eventApplicationService.getTypeList();
    }

    @Operation(summary = "测试环境报警")
    @PostMapping("/set")
    public ResponseDTO<Object> setEvent(@RequestBody DeviceDTO deviceDTO) {
        AddEventCommand command = new AddEventCommand();
        if (deviceDTO.getDeviceType().equals("环境档案")) {

            AlarmlevelDetailEntity alarmlevelDetailEntity = eventApplicationService
                    .checkEnvironment(deviceDTO.getEnvironmentAlarmInfo());
            if (alarmlevelDetailEntity == null) {
                return ResponseDTO.ok("无报警");
            }
            command.setEnvironmentValue(deviceDTO.getEnvironmentAlarmInfo().getValue());
            command.setType("环境报警");
            command.setLevel(alarmlevelDetailEntity.getLevel());
            command.setEnvironmentId(deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId());
            command.setEnvironmentUnit(deviceDTO.getEnvironmentAlarmInfo().getUnit());
            command.setAlarmlevelId(alarmlevelDetailEntity.getAlarmlevelId());
            eventApplicationService.createEvent(command);
        }

        if (deviceDTO.getDeviceType().equals("设备档案")) {
            ThresholdValueEntity thresholdValueEntity = thresholdApplicationService.checkEquipmentInfo(
                    deviceDTO.getEquipmentInfo());
            if (thresholdValueEntity == null) {
                return ResponseDTO.ok("无报警");
            }
            command.setEquipmentId(deviceDTO.getEquipmentInfo().getEquipmentId());
            command.setType("设备报警");
            command.setLevel(thresholdValueEntity.getLevel());
            command.setThresholdId(thresholdValueEntity.getThresholdId());
            command.setEquipmentValue(deviceDTO.getEquipmentInfo().getValue());
            eventApplicationService.createEvent(command);
        }

        return ResponseDTO.ok();
    }

    @Operation(summary = "报警统计")
    @GetMapping("/getWeekStatistics")
    public ResponseDTO<List<EventEchartDTO>> getWeekStatistics(StatisticsQuery query) {
        return ResponseDTO.ok(eventApplicationService.getWeekStatistics(query.getDayType()));
    }

    @Operation(summary = "报警区域统计")
    @GetMapping("/getAreaStatistics")
    public ResponseDTO<List<AreaStatisticsDTO>> getAreaStatistics(AreaStatisticsQuery query) {
        return ResponseDTO.ok(eventApplicationService.getAreaStatistics(query));
    }

    @Operation(summary = "报警数量全部统计")
    @GetMapping("/eventTotal")
    public ResponseDTO<EventTotalDTO> getEventTotal() {
        return ResponseDTO.ok(eventApplicationService.getEventTotal());
    }

    @Operation(summary = "历史报警数量全部统计")
    @GetMapping("/eventHistory")
    public ResponseDTO<HistoryEventEchart> getHistoryEventAll(StatisticsQuery query) {
        return ResponseDTO.ok(eventApplicationService.getHistoryEventAll(query));
    }

    @Operation(summary = "数据大屏获取环境档案的历史报警数量")
    @GetMapping("/envrionment")
    public ResponseDTO<EnvironmentStockEchart> getEnvrionmentEventAllWeek(EnvironmentEventQuery query) {
        return ResponseDTO.ok(eventApplicationService.getEnvrionmentEventAllWeek(query));
    }

    @Operation(summary = "数据大屏获取环境档案区域的历史报警数量")
    @GetMapping("/envrionmentArea")
    public ResponseDTO<EnvironmentStockEchart> getEnvrionmentAreaEventAll(EnvironmentEventQuery query) {
        return ResponseDTO.ok(eventApplicationService.getEnvrionmentAreaEventAll(query));
    }

    @Operation(summary = "数据大屏根据获取环境档案id的历史报警数量")
    @GetMapping("/envrionmentById")
    public ResponseDTO<EnvironmentStockEchart> getEnvironmentEventById(EnvironmentEventQuery query) {
        return ResponseDTO.ok(eventApplicationService.getEnvironmentEventById(query));
    }

    @Operation(summary = "数据大屏根据获取不同区域的历史报警数量")
    @GetMapping("/getAreaStatisticsTotal")
    public ResponseDTO<EventEchartOneDTO> getAreaStatisticsTotal(StatisticsQuery query) {
        return ResponseDTO.ok(eventApplicationService.getAreaStatisticsTotal(query));
    }

    @Operation(summary = "数据大屏根据获取不同类型的全部报警数量")
    @GetMapping("/getAllEventEchart")
    public ResponseDTO<List<AllEventEchartDTO>> getAllEventEchart() {
        return ResponseDTO.ok(eventApplicationService.getAllEventEchart());
    }

    @Operation(summary = "数据大屏根据获取不同类型的全部报警数量")
    @GetMapping("/getAllEquipmentAreaEchart")
    public ResponseDTO<List<AllEventEchartDTO>> getAllEquipmentAreaEchart() {
        return ResponseDTO.ok(eventApplicationService.getAllEquipmentAreaEchart());
    }

    @Operation(summary = "数据大屏根据获取不同类型的全部报警数量")
    @GetMapping("/getAllEnvironmentAreaEchart")
    public ResponseDTO<List<AllEventEchartDTO>> getAllEnvironmentAreaEchart() {
        return ResponseDTO.ok(eventApplicationService.getAllEnvironmentAreaEchart());
    }

}
