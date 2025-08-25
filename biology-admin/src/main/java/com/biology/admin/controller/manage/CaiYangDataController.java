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
import com.biology.domain.manage.caiYangData.CaiYangDataApplicationService;
import com.biology.domain.manage.caiYangData.command.AddCaiYangDataCommand;
import com.biology.domain.manage.caiYangData.command.UpdateCaiYangDataCommand;
import com.biology.domain.manage.caiYangData.dto.CaiYangDataDTO;
import com.biology.domain.manage.caiYangData.query.CaiYangDataQuery;
import com.biology.domain.manage.nongDuData.dto.NongDuDataDTO;
import com.biology.domain.manage.nongDuData.query.NongDuDataQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "采样数据", description = "采样数据的增删查改")
@RestController
@RequestMapping("/manage/caiYangData")
@Validated
@RequiredArgsConstructor
public class CaiYangDataController extends BaseController {
    private final CaiYangDataApplicationService caiYangDataApplicationService;

    @Operation(summary = "添加采样数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddCaiYangDataCommand command) {
        caiYangDataApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新采样数据")
    @PostMapping("/{caiYangDataId}")
    public ResponseDTO<Void> update(@RequestBody UpdateCaiYangDataCommand command) {
        caiYangDataApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除采样数据")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        caiYangDataApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取采样数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<CaiYangDataDTO>> list(CaiYangDataQuery query) {
        PageDTO<CaiYangDataDTO> list = caiYangDataApplicationService.getCaiYangDatas(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取采样数据信息")
    @GetMapping("/{caiYangDataId}")
    public ResponseDTO<CaiYangDataDTO> info(
            @PathVariable(value = "caiYangDataId", required = false) Long caiYangDataId) {
        CaiYangDataDTO caiYangDataDTO = caiYangDataApplicationService.getCaiYangDataInfo(caiYangDataId);
        return ResponseDTO.ok(caiYangDataDTO);
    }

    @Operation(summary = "采样数据列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, CaiYangDataQuery query) {
        PageDTO<CaiYangDataDTO> list = caiYangDataApplicationService.getCaiYangDatas(query);
        CustomExcelUtil.writeToResponse(list.getRows(), CaiYangDataDTO.class, response);
    }

}
