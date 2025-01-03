package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.alarm.AlarmApplicationService;
import com.biology.domain.manage.alarm.command.AddAlarmCommand;
import com.biology.domain.manage.alarm.command.UpdateAlarmCommand;
import com.biology.domain.manage.alarm.dto.AlarmDTO;
import com.biology.domain.manage.alarm.query.AlarmQuery;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "物料报警", description = "物料报警的增删查改")
@RestController
@RequestMapping("/manage/alarm")
@Validated
@RequiredArgsConstructor
public class AlarmController extends BaseController {
    private final AlarmApplicationService alarmApplicationService;

    @Operation(summary = "创建物料报警")
    @PostMapping
    public ResponseDTO<Void> createPolicy(@RequestBody AddAlarmCommand command) {
        alarmApplicationService.addAlarm(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取物料报警信息")
    @GetMapping("{alarmId}")
    public ResponseDTO<AlarmDTO> info(@PathVariable(value = "alarmId", required = false) Long alarmId) {
        AlarmDTO alarmDTO = alarmApplicationService.get(alarmId);
        return ResponseDTO.ok(alarmDTO);
    }

    @Operation(summary = "获取物料报警列表")
    @GetMapping
    public ResponseDTO<PageDTO<AlarmDTO>> list(AlarmQuery query) {
        PageDTO<AlarmDTO> list = alarmApplicationService.getAlarmList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "跟新物料报警")
    @PutMapping("{alarmId}")
    public ResponseDTO<Void> edit(@RequestBody UpdateAlarmCommand command) {
        alarmApplicationService.updateAlarm(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除物料报警")
    @DeleteMapping
    public ResponseDTO<Void> remove(@RequestParam @NotNull List<Long> alarmIds) {
        alarmApplicationService.deleteAlarms(alarmIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "物料列表导入")
    @PostMapping("/excel")
    public ResponseDTO<Void> importAlarmByExcel(MultipartFile file) {
        List<AddAlarmCommand> commands = CustomExcelUtil.readFromRequest(AddAlarmCommand.class, file);

        for (AddAlarmCommand command : commands) {
            alarmApplicationService.addAlarm(command);
        }
        return ResponseDTO.ok();
    }

    /**
     * 下载批量导入模板
     */
    @Operation(summary = "物料导入excel下载")
    @GetMapping("/excelTemplate")
    public void downloadExcelTemplate(HttpServletResponse response) {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new AddAlarmCommand()), AddAlarmCommand.class,
                response);
    }
}
