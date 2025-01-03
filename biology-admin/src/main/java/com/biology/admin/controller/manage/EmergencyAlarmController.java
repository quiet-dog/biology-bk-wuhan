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
import com.biology.domain.manage.emergencyAlarm.EmergencyAlarmApplicationService;
import com.biology.domain.manage.emergencyAlarm.command.AddEmergencyAlarmCommand;
import com.biology.domain.manage.emergencyAlarm.command.UpdateEmergencyAlarmCommand;
import com.biology.domain.manage.emergencyAlarm.dto.EmergencyAlarmDTO;
import com.biology.domain.manage.emergencyAlarm.query.EmergencyAlarmQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "报警信息API", description = "报警信息的增删查改")
@RestController
@RequestMapping("/manage/emergencyAlarm")
@Validated
@RequiredArgsConstructor
public class EmergencyAlarmController extends BaseController {
    private final EmergencyAlarmApplicationService emergencyAlarmApplicationService;

    @Operation(summary = "添加报警信息")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEmergencyAlarmCommand command) {
        emergencyAlarmApplicationService.addEmergencyAlarm(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新报警信息")
    @PutMapping("/{emergencyAlarmId}")
    public ResponseDTO<Void> update(@PathVariable Long emergencyAlarmId,
            @RequestBody UpdateEmergencyAlarmCommand command) {
        command.setEmergencyAlarmId(emergencyAlarmId);
        emergencyAlarmApplicationService.updateEmergencyAlarm(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除报警信息")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> emergencyAlarmIds) {
        emergencyAlarmApplicationService.deleteEmergencyAlarms(emergencyAlarmIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取报警信息详情")
    @GetMapping("/{emergencyAlarmId}")
    public ResponseDTO<EmergencyAlarmDTO> getEmergencyAlarmInfo(
            @PathVariable(value = "emergencyAlarmId", required = false) Long emergencyAlarmId) {
        EmergencyAlarmDTO emergencyAlarmDetailDTO = emergencyAlarmApplicationService
                .getEmergencyAlarm(emergencyAlarmId);
        return ResponseDTO.ok(emergencyAlarmDetailDTO);
    }

    @Operation(summary = "获取报警信息列表")
    @GetMapping
    public ResponseDTO<PageDTO<EmergencyAlarmDTO>> list(EmergencyAlarmQuery query) {
        PageDTO<EmergencyAlarmDTO> list = emergencyAlarmApplicationService.searchEmergencyAlarms(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "用户列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, EmergencyAlarmQuery query) {
        PageDTO<EmergencyAlarmDTO> userList = emergencyAlarmApplicationService.searchEmergencyAlarms(query);
        CustomExcelUtil.writeToResponse(userList.getRows(), EmergencyAlarmDTO.class, response);
    }
}
