package com.biology.admin.controller.manage;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.xwAlarm.XwAlarmApplicationService;
import com.biology.domain.manage.xwAlarm.command.AddXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.command.UpdateXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.dto.XwAlarmDTO;
import com.biology.domain.manage.xwAlarm.query.XwAlarmQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "行为数据", description = "行为数据的增删查改")
@RestController
@RequestMapping("/manage/xwAlarm")
@Validated
@RequiredArgsConstructor
public class XwAlarmController {
    private final XwAlarmApplicationService xwAlarmApplicationService;

    @Operation(summary = "添加行为数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddXwAlarmCommand command) {
        xwAlarmApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新行为数据")
    @PostMapping("/{xwAlarmId}")
    public ResponseDTO<Void> update(@RequestBody UpdateXwAlarmCommand command) {
        xwAlarmApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除行为数据")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        xwAlarmApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取行为数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<XwAlarmDTO>> list(XwAlarmQuery query) {
        PageDTO<XwAlarmDTO> list = xwAlarmApplicationService.getXwAlarms(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取行为数据信息")
    @GetMapping("/{xwAlarmId}")
    public ResponseDTO<XwAlarmDTO> info(@PathVariable(value = "xwAlarmId", required = false) Long xwAlarmId) {
        XwAlarmDTO xwAlarmDTO = xwAlarmApplicationService.getXwAlarmInfo(xwAlarmId);
        return ResponseDTO.ok(xwAlarmDTO);
    }
}
