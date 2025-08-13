package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.xlArchive.XlArchiveApplicationService;
import com.biology.domain.manage.xlArchive.command.AddXlArchiveCommand;
import com.biology.domain.manage.xlArchive.command.UpdateXlArchiveCommand;
import com.biology.domain.manage.xlArchive.dto.XlArchiveDTO;
import com.biology.domain.manage.xlArchive.query.XlArchiveQuery;
import com.biology.domain.manage.xwAlarm.dto.XwAlarmDTO;
import com.biology.domain.manage.xwAlarm.query.XwAlarmQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "心理健康档案", description = "心理健康档案的增删查改")
@RestController
@RequestMapping("/manage/xlArchive")
@Validated
@RequiredArgsConstructor
public class XlArchiveController extends BaseController {
    private final XlArchiveApplicationService xlArchiveApplicationService;

    @Operation(summary = "添加心理健康档案")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddXlArchiveCommand command) {
        xlArchiveApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新心理健康档案")
    @PostMapping("/{xlArchiveId}")
    public ResponseDTO<Void> update(@RequestBody UpdateXlArchiveCommand command) {
        xlArchiveApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除心理健康档案")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        xlArchiveApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取心理健康档案列表")
    @GetMapping
    public ResponseDTO<PageDTO<XlArchiveDTO>> list(XlArchiveQuery query) {
        PageDTO<XlArchiveDTO> list = xlArchiveApplicationService.getXlArchives(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取心理健康档案信息")
    @GetMapping("/{xlArchiveId}")
    public ResponseDTO<XlArchiveDTO> info(@PathVariable(value = "xlArchiveId", required = false) Long xlArchiveId) {
        XlArchiveDTO xlArchiveDTO = xlArchiveApplicationService.getXlArchiveInfo(xlArchiveId);
        return ResponseDTO.ok(xlArchiveDTO);
    }

    @Operation(summary = "心理健康档案列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, XlArchiveQuery query) {
        PageDTO<XlArchiveDTO> list = xlArchiveApplicationService.getXlArchives(query);
        CustomExcelUtil.writeToResponse(list.getRows(), XlArchiveDTO.class, response);
    }
}
