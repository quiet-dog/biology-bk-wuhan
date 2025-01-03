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
import com.biology.domain.manage.healthy.HealthyApplicationService;
import com.biology.domain.manage.healthy.command.AddHealthyCommand;
import com.biology.domain.manage.healthy.command.UpdateHealthyCommand;
import com.biology.domain.manage.healthy.dto.HealthyAlarmEchartDTO;
import com.biology.domain.manage.healthy.dto.HealthyDTO;
import com.biology.domain.manage.healthy.dto.HealthyStockEchartDTO;
import com.biology.domain.manage.healthy.query.HealthyAlarmQuery;
import com.biology.domain.manage.healthy.query.HealthyOneQuery;
import com.biology.domain.manage.healthy.query.HealthyQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "人员健康数据API", description = "人员健康数据的增删查改")
@RestController
@RequestMapping("/manage/healthy")
@Validated
@RequiredArgsConstructor
public class HealthyController extends BaseController {
    private final HealthyApplicationService healthyApplicationService;

    @Operation(summary = "添加人员健康数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddHealthyCommand command) {
        healthyApplicationService.addHealthy(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新添加人员健康数据")
    @PutMapping("/{healthyId}")
    public ResponseDTO<Void> update(@PathVariable("healthyId") Long healthyId,
            @Validated @RequestBody UpdateHealthyCommand command) {
        command.setHealthyId(healthyId);
        healthyApplicationService.updateHealthy(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除添加人员健康数据")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> healthyIds) {
        healthyApplicationService.deleteHealthys(healthyIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取添加人员健康数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<HealthyDTO>> list(HealthyQuery query) {
        PageDTO<HealthyDTO> list = healthyApplicationService.getHealthyList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取人员健康数据信息")
    @GetMapping("/{healthyId}")
    public ResponseDTO<HealthyDTO> info(
            @PathVariable(value = "healthyId", required = false) Long healthyId) {
        HealthyDTO healthyDTO = healthyApplicationService.getHealthyInfo(healthyId);
        return ResponseDTO.ok(healthyDTO);
    }

    @Operation(summary = "获取人员健康报警温度血压统计信息")
    @GetMapping("/alarmStockType")
    public ResponseDTO<List<HealthyAlarmEchartDTO>> getAlarmStockType(HealthyAlarmQuery query) {
        List<HealthyAlarmEchartDTO> list = healthyApplicationService.getAlarmStockType(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "数据大屏获取人员的健康数据统计")
    @GetMapping("/healthyStock")
    public ResponseDTO<HealthyStockEchartDTO> getHealthyStock(HealthyOneQuery query) {
        return ResponseDTO.ok(healthyApplicationService.getHealthyStock(query));
    }

}
