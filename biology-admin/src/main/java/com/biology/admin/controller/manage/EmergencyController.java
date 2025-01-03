package com.biology.admin.controller.manage;

import java.util.List;

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

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.emergency.EmergencyApplicationService;
import com.biology.domain.manage.emergency.command.AddEmergencyCommand;
import com.biology.domain.manage.emergency.command.UpdateEmergencyCommand;
import com.biology.domain.manage.emergency.dto.EmergencyDTO;
import com.biology.domain.manage.emergency.dto.EmergencyDetailDTO;
import com.biology.domain.manage.emergency.query.SearchEmergencyQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "应急预案API", description = "应急预案的增删查改")
@RestController
@RequestMapping("/manage/emergency")
@Validated
@RequiredArgsConstructor
public class EmergencyController extends BaseController {
    private final EmergencyApplicationService emergencyApplicationService;

    @Operation(summary = "添加应急预案")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEmergencyCommand command) {
        emergencyApplicationService.createEmergency(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新应急预案")
    @PutMapping("/{emergencyId}")
    public ResponseDTO<Void> update(@PathVariable Long emergencyId, @RequestBody UpdateEmergencyCommand command) {
        command.setEmergencyId(emergencyId);
        emergencyApplicationService.updateEmergency(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除应急预案")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> emergencyIds) {
        emergencyApplicationService.deleteEmergencies(emergencyIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取应急预案详情")
    @GetMapping("/{emergencyId}")
    public ResponseDTO<EmergencyDetailDTO> getEmergencyInfo(
            @PathVariable(value = "emergencyId", required = false) Long emergencyId) {
        EmergencyDetailDTO emergencyDetailDTO = emergencyApplicationService.getEmergencyInfo(emergencyId);
        return ResponseDTO.ok(emergencyDetailDTO);
    }

    @Operation(summary = "获取应急预案列表")
    @GetMapping
    public ResponseDTO<PageDTO<EmergencyDTO>> list(SearchEmergencyQuery query) {
        PageDTO<EmergencyDTO> list = emergencyApplicationService.getEmergencyList(query);
        return ResponseDTO.ok(list);
    }
}
