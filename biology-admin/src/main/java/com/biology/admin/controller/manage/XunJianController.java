package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.page.PageDTO;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.xunJian.XunJianApplicationService;
import com.biology.domain.manage.xunJian.command.AddXunJianCommand;
import com.biology.domain.manage.xunJian.command.UpdateXunJianCommand;
import com.biology.domain.manage.xunJian.dto.XunJianDTO;
import com.biology.domain.manage.xunJian.query.XunJianQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "巡检", description = "巡检的增删查改")
@RestController
@RequestMapping("/manage/xunJian")
@Validated
@RequiredArgsConstructor
public class XunJianController extends BaseController {
    private final XunJianApplicationService xunJianApplicationService;

    @Operation(summary = "添加巡检")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddXunJianCommand command) {
        xunJianApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新巡检")
    @PutMapping("/{xunJianId}")
    public ResponseDTO<Void> update(@PathVariable Long xunJianId, @RequestBody UpdateXunJianCommand command) {
        command.setXunJianId(xunJianId);
        xunJianApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除巡检")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        xunJianApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取巡检列表")
    @GetMapping
    public ResponseDTO<PageDTO<XunJianDTO>> list(XunJianQuery query) {
        PageDTO<XunJianDTO> list = xunJianApplicationService.getXunJians(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取巡检信息")
    @GetMapping("/{xunJianId}")
    public ResponseDTO<XunJianDTO> info(@PathVariable(value = "xunJianId", required = false) Long xunJianId) {
        XunJianDTO xunJianDTO = xunJianApplicationService.getXunJianInfo(xunJianId);
        return ResponseDTO.ok(xunJianDTO);
    }

    @Operation(summary = "巡检数据列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, XunJianQuery query) {
        PageDTO<XunJianDTO> list = xunJianApplicationService.getXunJians(query);
        CustomExcelUtil.writeToResponse(list.getRows(), XunJianDTO.class, response);
    }

    @Operation(summary = "启用巡检")
    @PostMapping("/enable/{xunJianId}")
    public ResponseDTO<Void> enable(@PathVariable Long xunJianId) {
        xunJianApplicationService.enable(xunJianId);
        return ResponseDTO.ok();
    }

    @Operation(summary = "禁用巡检")
    @PostMapping("/disable/{xunJianId}")
    public ResponseDTO<Void> disable(@PathVariable Long xunJianId) {
        xunJianApplicationService.disable(xunJianId);
        return ResponseDTO.ok();
    }
}
