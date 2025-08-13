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
import com.biology.domain.manage.xlFangAn.XlFangAnApplicationService;
import com.biology.domain.manage.xlFangAn.command.AddXlFangAnCommand;
import com.biology.domain.manage.xlFangAn.command.UpdateXlFangAnCommand;
import com.biology.domain.manage.xlFangAn.dto.XlFangAnDTO;
import com.biology.domain.manage.xlFangAn.query.XlFangAnQuery;
import com.biology.domain.manage.xwAlarm.dto.XwAlarmDTO;
import com.biology.domain.manage.xwAlarm.query.XwAlarmQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "心理测评方案", description = "心理测评方案的增删查改")
@RestController
@RequestMapping("/manage/xlFangAn")
@Validated
@RequiredArgsConstructor
public class XlFangAnController extends BaseController {
    private final XlFangAnApplicationService xlFangAnApplicationService;

    @Operation(summary = "添加心理测评方案")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddXlFangAnCommand command) {
        xlFangAnApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新心理测评方案")
    @PostMapping("/{xlFangAnId}")
    public ResponseDTO<Void> update(@RequestBody UpdateXlFangAnCommand command) {
        xlFangAnApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除心理测评方案")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        xlFangAnApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取心理测评方案列表")
    @GetMapping
    public ResponseDTO<PageDTO<XlFangAnDTO>> list(XlFangAnQuery query) {
        PageDTO<XlFangAnDTO> list = xlFangAnApplicationService.getXlFangAns(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取心理测评方案信息")
    @GetMapping("/{xlFangAnId}")
    public ResponseDTO<XlFangAnDTO> info(@PathVariable(value = "xlFangAnId", required = false) Long xlFangAnId) {
        XlFangAnDTO xlFangAnDTO = xlFangAnApplicationService.getXlFangAnInfo(xlFangAnId);
        return ResponseDTO.ok(xlFangAnDTO);
    }

    @Operation(summary = "获取心理测评方案组")
    @GetMapping("/getDeptGroup")
    public ResponseDTO<List<String>> getDeptGroup() {
        return ResponseDTO.ok(xlFangAnApplicationService.getDeptGroup());
    }

    @Operation(summary = "心理测评方案列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, XlFangAnQuery query) {
        PageDTO<XlFangAnDTO> list = xlFangAnApplicationService.getXlFangAns(query);
        CustomExcelUtil.writeToResponse(list.getRows(), XlFangAnDTO.class, response);
    }
}
