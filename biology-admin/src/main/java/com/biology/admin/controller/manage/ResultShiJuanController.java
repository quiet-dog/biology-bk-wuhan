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

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.shiJuan.ResultShiJuanApplicationService;
import com.biology.domain.manage.shiJuan.command.AddResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.command.UpdateResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.dto.ResultGanYuDTO;
import com.biology.domain.manage.shiJuan.dto.ResultShiJuanDTO;
import com.biology.domain.manage.shiJuan.dto.ShiJuanDTO;
import com.biology.domain.manage.shiJuan.dto.SubmitResultDTO;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanModel;
import com.biology.domain.manage.shiJuan.query.ResultShiJuanQuery;
import com.biology.domain.manage.shiJuan.query.ShiJuanQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "行为设备", description = "行为设备的增删查改")
@RestController
@RequestMapping("/manage/resultShiJuan")
@Validated
@RequiredArgsConstructor
public class ResultShiJuanController extends BaseController {
    private final ResultShiJuanApplicationService resultShiJuanApplicationService;

    @Operation(summary = "添加行为设备")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddResultShiJuanCommand command) {
        resultShiJuanApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新行为设备")
    @PostMapping("/{resultShiJuanId}")
    public ResponseDTO<Void> update(@RequestBody UpdateResultShiJuanCommand command) {
        resultShiJuanApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除行为设备")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        resultShiJuanApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取行为设备列表")
    @GetMapping
    public ResponseDTO<PageDTO<ResultShiJuanDTO>> list(ResultShiJuanQuery query) {
        PageDTO<ResultShiJuanDTO> list = resultShiJuanApplicationService.getResultShiJuans(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取行为设备信息")
    @GetMapping("/{resultShiJuanId}")
    public ResponseDTO<ResultShiJuanDTO> info(
            @PathVariable(value = "resultShiJuanId", required = false) Long resultShiJuanId) {
        ResultShiJuanDTO resultShiJuanDTO = resultShiJuanApplicationService.getResultShiJuanInfo(resultShiJuanId);
        return ResponseDTO.ok(resultShiJuanDTO);
    }

    @Operation(summary = "获取行为设备列表")
    @GetMapping("/listByCreator")
    public ResponseDTO<PageDTO<ResultShiJuanDTO>> listByCreator(ResultShiJuanQuery query) {
        PageDTO<ResultShiJuanDTO> list = resultShiJuanApplicationService.getResultShiJuansByCreator(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取行为设备列表")
    @GetMapping("/listByUser")
    public ResponseDTO<PageDTO<ResultShiJuanDTO>> listByUser(ResultShiJuanQuery query) {
        PageDTO<ResultShiJuanDTO> list = resultShiJuanApplicationService.getResultShiJuansByUser(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取试卷列表")
    @GetMapping("/shiJuanList")
    public ResponseDTO<PageDTO<ShiJuanDTO>> getShiJuanList(ShiJuanQuery query) {
        PageDTO<ShiJuanDTO> list = resultShiJuanApplicationService.getShiJuanList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取试卷列表")
    @PostMapping("/setResult")
    public ResponseDTO<Void> submitResult(@RequestBody SubmitResultDTO req) {
        resultShiJuanApplicationService.submitResult(req);
        return ResponseDTO.ok();
    }

    @Operation(summary = "设置干预")
    @PostMapping("/setGanYu")
    public ResponseDTO<Void> setGanYu(@RequestBody ResultGanYuDTO req) {
        resultShiJuanApplicationService.setGanYu(req);
        return ResponseDTO.ok();
    }
}
