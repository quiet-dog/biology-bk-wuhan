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
import com.biology.domain.manage.nongDuData.NongDuDataApplicationService;
import com.biology.domain.manage.nongDuData.command.AddNongDuDataCommand;
import com.biology.domain.manage.nongDuData.command.NongDuDTO;
import com.biology.domain.manage.nongDuData.command.UpdateNongDuDataCommand;
import com.biology.domain.manage.nongDuData.dto.NongDuDataDTO;
import com.biology.domain.manage.nongDuData.query.NongDuDataLuanShengQuery;
import com.biology.domain.manage.nongDuData.query.NongDuDataQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "监测数据", description = "监测数据的增删查改")
@RestController
@RequestMapping("/manage/nongDuData")
@Validated
@RequiredArgsConstructor
public class NongDuDataController extends BaseController {
    private final NongDuDataApplicationService nongDuDataApplicationService;

    @Operation(summary = "添加监测数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddNongDuDataCommand command) {
        nongDuDataApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新监测数据")
    @PostMapping("/{nongDuDataId}")
    public ResponseDTO<Void> update(@RequestBody UpdateNongDuDataCommand command) {
        nongDuDataApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除监测数据")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        nongDuDataApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取监测数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<NongDuDataDTO>> list(NongDuDataQuery query) {
        PageDTO<NongDuDataDTO> list = nongDuDataApplicationService.getNongDuDatas(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取监测数据信息")
    @GetMapping("/{nongDuDataId}")
    public ResponseDTO<NongDuDataDTO> info(
            @PathVariable(value = "nongDuDataId", required = false) Long nongDuDataId) {
        NongDuDataDTO nongDuDataDTO = nongDuDataApplicationService.getNongDuDataInfo(nongDuDataId);
        return ResponseDTO.ok(nongDuDataDTO);
    }

    @Operation(summary = "监测数据列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, NongDuDataQuery query) {
        PageDTO<NongDuDataDTO> list = nongDuDataApplicationService.getNongDuDatas(query);
        CustomExcelUtil.writeToResponse(list.getRows(), NongDuDataDTO.class, response);
    }

    @PostMapping("/nongDuDateGet")
    public void nongDuDateGet(@RequestBody NongDuDTO nDto) {
        System.out.println("/nongDuDateGet nDto = " + nDto);
        nongDuDataApplicationService.nongDuDateGet(nDto);
        return;
    }

    @PostMapping("/getNongDuDataOnlineHistory")
    public ResponseDTO<Object> getNongDuDataOnlineHistory(@RequestBody NongDuDataLuanShengQuery query) {
        Object result = nongDuDataApplicationService.getNongDuDataOnlineHistory(query);
        return ResponseDTO.ok(result);
    }

    @Operation(summary = "获取监测数据报警数量")
    @GetMapping("/getNongDuDataAlarmCount")
    public ResponseDTO<Object> getNongDuDataAlarmCount(@RequestParam String startTime, @RequestParam String endTime,
            @RequestParam List<String> deviceSn) {
        Object result = nongDuDataApplicationService.getNongDuDataAlarmCount(startTime, endTime, deviceSn);
        return ResponseDTO.ok(result);
    }

}
