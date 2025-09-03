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
import com.biology.domain.manage.xsData.XsDataApplicationService;
import com.biology.domain.manage.xsData.command.AddXsDataCommand;
import com.biology.domain.manage.xsData.command.UpdateXsDataCommand;
import com.biology.domain.manage.xsData.command.XsDataFun1DTO;
import com.biology.domain.manage.xsData.dto.XsDataDTO;
import com.biology.domain.manage.xsData.query.XsDataQuery;
import com.biology.domain.manage.xsDevice.dto.XsDeviceDTO;
import com.biology.domain.manage.xsDevice.query.XsDeviceQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "消杀数据", description = "消杀数据的增删查改")
@RestController
@RequestMapping("/manage/xsData")
@Validated
@RequiredArgsConstructor
public class XsDataController extends BaseController {
    private final XsDataApplicationService xsDataApplicationService;

    @Operation(summary = "添加消杀数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddXsDataCommand command) {
        xsDataApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新消杀数据")
    @PostMapping("/{xsDataId}")
    public ResponseDTO<Void> update(@RequestBody UpdateXsDataCommand command) {
        xsDataApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除消杀数据")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        xsDataApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取消杀数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<XsDataDTO>> list(XsDataQuery query) {
        PageDTO<XsDataDTO> list = xsDataApplicationService.getXsDatas(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取消杀数据信息")
    @GetMapping("/{xsDataId}")
    public ResponseDTO<XsDataDTO> info(@PathVariable(value = "xsDataId", required = false) Long xsDataId) {
        XsDataDTO xsDataDTO = xsDataApplicationService.getXsDataInfo(xsDataId);
        return ResponseDTO.ok(xsDataDTO);
    }

    @Operation(summary = "行为监测数据列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, XsDataQuery query) {
        PageDTO<XsDataDTO> list = xsDataApplicationService.getXsDatas(query);
        CustomExcelUtil.writeToResponse(list.getRows(), XsDataDTO.class, response);
    }

    @PostMapping("/xiaoSha")
    public ResponseDTO<Void> xiaoSha(@RequestBody List<XsDataFun1DTO> data) {
        System.out.println("/xiaoSha data = " + data);
        xsDataApplicationService.xiaoSha(data);
        return ResponseDTO.ok();
    }
}
