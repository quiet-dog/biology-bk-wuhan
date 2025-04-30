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
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.materials.command.AddMaterialsCommand;
import com.biology.domain.manage.materials.dto.MaterialsDTO;
import com.biology.domain.manage.materials.query.SearchMaterialsQuery;
import com.biology.domain.manage.moni.MoniApplicationService;
import com.biology.domain.manage.moni.command.AddMoniCommand;
import com.biology.domain.manage.moni.command.UpdateMoniCommand;
import com.biology.domain.manage.moni.dto.MoniDTO;
import com.biology.domain.manage.moni.query.SearchMoniQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "模拟", description = "模拟的增删查改")
@RestController
@RequestMapping("/manage/moni")
@Validated
@RequiredArgsConstructor
public class MoniController extends BaseController {

    private final MoniApplicationService moniApplicationService;

    @Operation(summary = "创建模拟")
    @PostMapping
    public ResponseDTO<Void> createMoni(@RequestBody AddMoniCommand command) {
        moniApplicationService.addMoni(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新模拟")
    @PutMapping("{moniId}")
    public ResponseDTO<Void> updateMoni(@PathVariable("moniId") Long moniId,
            @RequestBody UpdateMoniCommand command) {
        command.setMoniId(moniId);
        moniApplicationService.updateMoni(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除模拟")
    @DeleteMapping
    public ResponseDTO<Void> deleteMoni(@RequestBody List<Long> moniIds) {
        moniApplicationService.deleteMoni(moniIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取模拟列表")
    @GetMapping
    public ResponseDTO<PageDTO<MoniDTO>> list(SearchMoniQuery query) {
        PageDTO<MoniDTO> page = moniApplicationService.getMoniList(query);
        return ResponseDTO.ok(page);
    }

    @Operation(summary = "开启推送")
    @GetMapping("/start/{moniId}")
    public ResponseDTO<Void> startPush(@PathVariable("moniId") Long moniId) {
        moniApplicationService.startPush(moniId);
        return ResponseDTO.ok();
    }

    @Operation(summary = "停止推送")
    @GetMapping("/stop/{moniId}")
    public ResponseDTO<Void> stopPush(@PathVariable("moniId") Long moniId) {
        moniApplicationService.stopPush(moniId);
        return ResponseDTO.ok();
    }
}
