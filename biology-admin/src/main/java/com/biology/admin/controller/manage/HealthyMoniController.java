package com.biology.admin.controller.manage;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.healthyMoni.HealthyMoniApplicationService;
import com.biology.domain.manage.healthyMoni.command.AddHealthyMoniCommand;
import com.biology.domain.manage.healthyMoni.command.UpdateHealthyMoniCommand;
import com.biology.domain.manage.healthyMoni.dto.HealthyMoniDTO;
import com.biology.domain.manage.healthyMoni.query.HealthyMoniQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "健康模拟", description = "健康模拟的增删查改")
@RestController
@RequestMapping("/manage/healthyMoni")
@Validated
@RequiredArgsConstructor
public class HealthyMoniController extends BaseController {
    private final HealthyMoniApplicationService healthyMoniApplicationService;

    @Operation(summary = "创建模拟")
    @PostMapping
    public ResponseDTO<Void> createHealthyMoni(@RequestBody AddHealthyMoniCommand command) {
        healthyMoniApplicationService.addHealthyMoni(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新模拟")
    @PutMapping("{healthyMoniId}")
    public ResponseDTO<Void> updateHealthyMoni(@PathVariable("healthyMoniId") Long healthyMoniId,
            @RequestBody UpdateHealthyMoniCommand command) {
        command.setHealthyMoniId(healthyMoniId);
        healthyMoniApplicationService.updateHealthyMoni(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取模拟列表")
    @GetMapping
    public ResponseDTO<PageDTO<HealthyMoniDTO>> list(HealthyMoniQuery query) {
        PageDTO<HealthyMoniDTO> page = healthyMoniApplicationService.getHealthyMoniList(query);
        return ResponseDTO.ok(page);
    }

    @Operation(summary = "开启推送")
    @GetMapping("/start/{healthyMoniId}")
    public ResponseDTO<Void> startPush(@PathVariable("healthyMoniId") Long healthyMoniId) {
        // healthyMoniApplicationService.startPush(healthyMoniId);
        return ResponseDTO.ok();
    }

    @Operation(summary = "停止推送")
    @GetMapping("/stop/{healthyMoniId}")
    public ResponseDTO<Void> stopPush(@PathVariable("healthyMoniId") Long healthyMoniId) {
        // healthyMoniApplicationService.stopPush(healthyMoniId);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新模拟")
    @PostMapping("/batchUpdateHealthyMoni")
    public ResponseDTO<Void> batchUpdateHealthy(@RequestBody List<UpdateHealthyMoniCommand> commands) {
        healthyMoniApplicationService.batchUpdateHealthy(commands);
        return ResponseDTO.ok();
    }

    // @Operation(summary = "发送模拟数据")
    // @PostMapping("/send")
    // public ResponseDTO<Void> sendHealthyMoniData(@RequestBody
    // SendHealthyMoniDataCommand command) {
    // healthyMoniApplicationService.sendData(command);
    // return ResponseDTO.ok();
    // }
}
