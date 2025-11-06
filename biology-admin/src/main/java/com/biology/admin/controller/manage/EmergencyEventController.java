package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.emergencyEvent.EmergencyEventApplicationService;
import com.biology.domain.manage.emergencyEvent.command.AddEmergencyEventCommand;
import com.biology.domain.manage.emergencyEvent.command.UpdateEmergencyEventCommand;
import com.biology.domain.manage.emergencyEvent.dto.EmergencyEventDTO;
import com.biology.domain.manage.emergencyEvent.dto.EmergencyEventDetailDTO;
import com.biology.domain.manage.emergencyEvent.dto.EmergencyEventStaticDTO;
import com.biology.domain.manage.emergencyEvent.query.EmergencyEventQuery;
import com.biology.domain.manage.event.dto.EventEchartDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "应急调度报警事件API", description = "应急调度报警事件的增删查改")
@RestController
@RequestMapping("/manage/emergencyEvent")
@Validated
@RequiredArgsConstructor
public class EmergencyEventController {
    private final EmergencyEventApplicationService emergencyEventApplicationService;

    @Operation(summary = "添加应急调度报警事件")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEmergencyEventCommand command) {
        emergencyEventApplicationService.addEmergencyEvent(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新应急调度报警事件")
    @PutMapping("/{emergencyEventId}")
    public ResponseDTO<Void> update(@PathVariable Long emergencyEventId,
            @RequestBody UpdateEmergencyEventCommand command) {
        command.setEmergencyEventId(emergencyEventId);
        emergencyEventApplicationService.updateEmergencyEvent(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除应急调度报警事件")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> emergencyEventIds) {
        emergencyEventApplicationService.deleteEmergencyEvents(emergencyEventIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取应急调度报警事件详情")
    @GetMapping("/{emergencyEventId}")
    public ResponseDTO<EmergencyEventDetailDTO> getEmergencyEventInfo(
            @PathVariable(value = "emergencyEventId", required = false) Long emergencyEventId) {
        EmergencyEventDetailDTO emergencyEventDetailDTO = emergencyEventApplicationService
                .getEmergencyEvent(emergencyEventId);
        return ResponseDTO.ok(emergencyEventDetailDTO);
    }

    @Operation(summary = "获取应急调度报警事件列表")
    @GetMapping
    public ResponseDTO<PageDTO<EmergencyEventDTO>> list(EmergencyEventQuery query) {
        PageDTO<EmergencyEventDTO> list = emergencyEventApplicationService.searchEmergencyEvents(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "导出应急调度报警事件列表")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, EmergencyEventQuery query) {
        PageDTO<EmergencyEventDTO> userList = emergencyEventApplicationService.searchEmergencyEvents(query);
        CustomExcelUtil.writeToResponse(userList.getRows(), EmergencyEventDTO.class, response);
    }

    @Operation(summary = "用户列表导出")
    @GetMapping("/getStock")
    public ResponseDTO<EventEchartDTO> getStock(EmergencyEventStaticDTO query) {
        return ResponseDTO.ok(emergencyEventApplicationService.getStock(query));
    }

    @GetMapping("/getStockByHandle")
    public ResponseDTO<Object> getStockByHandle(EmergencyEventStaticDTO query) {
        return ResponseDTO.ok(emergencyEventApplicationService.getStockByHandle(query));
    }
}
