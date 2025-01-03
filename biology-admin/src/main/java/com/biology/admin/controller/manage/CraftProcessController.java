package com.biology.admin.controller.manage;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.craftprocess.CraftProcessApplicationService;
import com.biology.domain.manage.craftprocess.command.AddCraftProcessCommand;
import com.biology.domain.manage.craftprocess.command.UpdateCraftProcessCommand;
import com.biology.domain.manage.craftprocess.dto.CraftProcessDTO;
import com.biology.domain.manage.craftprocess.query.SearchCraftProcessQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "工艺流程图API", description = "工艺流程图的增删查改")
@RestController
@RequestMapping("/manage/craft-process")
@Validated
@RequiredArgsConstructor
public class CraftProcessController extends BaseController {

    private final CraftProcessApplicationService craftProcessApplicationService;

    @Operation(summary = "添加工艺流程图")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddCraftProcessCommand command) {
        craftProcessApplicationService.addCraftProcess(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新工艺流程图")
    @PutMapping("/{craftProcessId}")
    public ResponseDTO<Void> update(@PathVariable Long craftProcessId, @RequestBody UpdateCraftProcessCommand command) {
        command.setCraftProcessId(craftProcessId);
        craftProcessApplicationService.updateCraftProcess(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除工艺流程图")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> craftProcessIds) {
        craftProcessApplicationService.deleteCraftProcesses(craftProcessIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取工艺流程图信息")
    @GetMapping("/{craftProcessId}")
    public ResponseDTO<CraftProcessDTO> info(@PathVariable Long craftProcessId) {
        CraftProcessDTO craftProcessDTO = craftProcessApplicationService.getCraftProcessInfo(craftProcessId);
        return ResponseDTO.ok(craftProcessDTO);
    }

    @Operation(summary = "获取工艺流程图列表")
    @GetMapping
    public ResponseDTO<PageDTO<CraftProcessDTO>> list(SearchCraftProcessQuery query) {
        PageDTO<CraftProcessDTO> list = craftProcessApplicationService.getCraftProcessList(query);
        return ResponseDTO.ok(list);
    }
}
