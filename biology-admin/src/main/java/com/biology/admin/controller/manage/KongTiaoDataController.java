package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.domain.manage.kongTiaoData.KongTiaoDataApplicationService;
import com.biology.domain.manage.kongTiaoData.command.AddKongTiaoDataCommand;
import com.biology.domain.manage.kongTiaoData.command.UpdateKongTiaoDataCommand;
import com.biology.domain.manage.kongTiaoData.dto.KongTiaoDataDTO;
import com.biology.domain.manage.kongTiaoData.query.KongTiaoDataQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/manage/kongTiaoData")
@RequiredArgsConstructor
public class KongTiaoDataController extends BaseController {
    private final KongTiaoDataApplicationService kongTiaoDataApplicationService;

    @Operation(summary = "添加空调设备数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddKongTiaoDataCommand command) {
        kongTiaoDataApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新空调设备数据")
    @PostMapping("/{kongTiaoDataId}")
    public ResponseDTO<Void> update(@RequestBody UpdateKongTiaoDataCommand command) {
        kongTiaoDataApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除空调设备数据")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        kongTiaoDataApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取空调设备数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<KongTiaoDataDTO>> list(KongTiaoDataQuery query) {
        PageDTO<KongTiaoDataDTO> list = kongTiaoDataApplicationService.getKongTiaoDatas(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取空调设备数据信息")
    @GetMapping("/{kongTiaoDataId}")
    public ResponseDTO<KongTiaoDataDTO> info(
            @PathVariable(value = "kongTiaoDataId", required = false) Long kongTiaoDataId) {
        KongTiaoDataDTO kongTiaoDataDTO = kongTiaoDataApplicationService.getKongTiaoDataInfo(kongTiaoDataId);
        return ResponseDTO.ok(kongTiaoDataDTO);
    }

    @Operation(summary = "空调设备数据列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, KongTiaoDataQuery query) {
        PageDTO<KongTiaoDataDTO> list = kongTiaoDataApplicationService.getKongTiaoDatas(query);
        CustomExcelUtil.writeToResponse(list.getRows(), KongTiaoDataDTO.class, response);
    }
}
