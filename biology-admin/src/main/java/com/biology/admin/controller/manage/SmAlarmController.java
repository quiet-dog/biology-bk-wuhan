package com.biology.admin.controller.manage;

import java.util.List;
import java.util.Map;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.smAlarm.SmAlarmApplicationService;
import com.biology.domain.manage.smAlarm.command.AddSmAlarmCommand;
import com.biology.domain.manage.smAlarm.command.UpdateSmAlarmCommand;
import com.biology.domain.manage.smAlarm.dto.SmAlarmDTO;
import com.biology.domain.manage.smAlarm.query.SmAlarmQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "生命报警数据", description = "生命报警数据数据的增删查改")
@RestController
@RequestMapping("/manage/smAlarm")
@Validated
@RequiredArgsConstructor
public class SmAlarmController extends BaseController {
    private final SmAlarmApplicationService smAlarmApplicationService;

    @Operation(summary = "添加生命报警数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddSmAlarmCommand command) {
        smAlarmApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新生命报警数据")
    @PostMapping("/{smAlarmId}")
    public ResponseDTO<Void> update(@RequestBody UpdateSmAlarmCommand command) {
        smAlarmApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除生命报警数据")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        smAlarmApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取生命报警数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<SmAlarmDTO>> list(SmAlarmQuery query) {
        PageDTO<SmAlarmDTO> list = smAlarmApplicationService.getSmAlarms(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取生命报警数据信息")
    @GetMapping("/{smAlarmId}")
    public ResponseDTO<SmAlarmDTO> info(@PathVariable(value = "smAlarmId", required = false) Long smAlarmId) {
        SmAlarmDTO smAlarmDTO = smAlarmApplicationService.getSmAlarmInfo(smAlarmId);
        return ResponseDTO.ok(smAlarmDTO);
    }

    @GetMapping("/getLiShiYiChangPaiMing")
    public ResponseDTO<Map<String, Object>> getLiShiYiChangPaiMing() {
        return ResponseDTO.ok(smAlarmApplicationService.getLiShiYiChangPaiMing());
    }

    @GetMapping("/getBaoJingCiShuTongJiByRecentWeek")
    public ResponseDTO<Map<String, Object>> getBaoJingCiShuTongJiByRecentWeek() {
        return ResponseDTO.ok(smAlarmApplicationService.getBaoJingCiShuTongJiByRecentWeek());
    }

    @GetMapping("/getDayExceptionCount")
    public ResponseDTO<Long> getDayExceptionCount() {
        return ResponseDTO.ok(smAlarmApplicationService.getDayExceptionCount());
    }
}
