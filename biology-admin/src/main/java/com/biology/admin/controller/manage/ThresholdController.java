package com.biology.admin.controller.manage;

import java.util.List;

import javax.validation.constraints.NotNull;

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
import com.biology.domain.manage.threshold.ThresholdApplicationService;
import com.biology.domain.manage.threshold.command.AddThresholdCommand;
import com.biology.domain.manage.threshold.dto.OnlineThresholdEchart;
import com.biology.domain.manage.threshold.dto.ThresholdDTO;
import com.biology.domain.manage.threshold.query.ThresholdSearch;
import com.biology.domain.manage.threshold.command.UpdateThresholdCommand;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "阀值", description = "阀值的增删查改")
@RestController
@RequestMapping("/manage/threshold")
@Validated
@RequiredArgsConstructor
public class ThresholdController extends BaseController {
    private final ThresholdApplicationService thresholdApplicationService;

    @Operation(summary = "创建阀值")
    @PostMapping
    public ResponseDTO<Void> createPolicy(@RequestBody AddThresholdCommand command) {
        thresholdApplicationService.createThreshold(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取阀值信息")
    @GetMapping("{thresholdId}")
    public ResponseDTO<ThresholdDTO> info(@PathVariable(value = "thresholdId", required = false) Long thresholdId) {
        ThresholdDTO thresholdDTO = thresholdApplicationService.getThreshold(thresholdId);
        return ResponseDTO.ok(thresholdDTO);
    }

    @Operation(summary = "获取阀值列表")
    @GetMapping
    public ResponseDTO<PageDTO<ThresholdDTO>> list(ThresholdSearch query) {
        PageDTO<ThresholdDTO> list = thresholdApplicationService.searchThresholds(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "跟新阀值")
    @PutMapping("{thresholdId}")
    public ResponseDTO<Void> edit(@PathVariable Long thresholdId, @RequestBody UpdateThresholdCommand command) {
        command.setThresholdId(thresholdId);
        thresholdApplicationService.updateThreshold(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除阀值")
    @DeleteMapping
    public ResponseDTO<Void> remove(@RequestParam @NotNull List<Long> thresholdIds) {
        thresholdApplicationService.deleteThresholds(thresholdIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "首页获取设备在线状态图表")
    @GetMapping("/online")
    public ResponseDTO<OnlineThresholdEchart> getDeviceStatusEchart() {
        return ResponseDTO.ok(thresholdApplicationService.getDeviceStatusEchart());
    }

    @Operation(summary = "获取运行时长")
    @GetMapping("/runTime/{thresholdId}")
    public ResponseDTO<String> getRunTime(@PathVariable Long thresholdId) {
        return ResponseDTO.ok(thresholdApplicationService.getRunTime(thresholdId));
    }
}
