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
import com.biology.domain.manage.smData.SmDataApplicationService;
import com.biology.domain.manage.smData.command.AddSmDataCommand;
import com.biology.domain.manage.smData.command.UpdateSmDataCommand;
import com.biology.domain.manage.smData.dto.SmDataDTO;
import com.biology.domain.manage.smData.query.SmDataLuanShengQuiery;
import com.biology.domain.manage.smData.query.SmDataQuery;
import com.biology.domain.manage.smDevice.dto.SmDeviceDTO;
import com.biology.domain.manage.smDevice.query.SmDeviceQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "生命设备数据", description = "生命设备数据数据的增删查改")
@RestController
@RequestMapping("/manage/smData")
@Validated
@RequiredArgsConstructor
public class SmDataController extends BaseController {
    private final SmDataApplicationService smDataApplicationService;

    @Operation(summary = "添加生命设备数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddSmDataCommand command) {
        smDataApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新生命设备数据")
    @PostMapping("/{smDataId}")
    public ResponseDTO<Void> update(@RequestBody UpdateSmDataCommand command) {
        smDataApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除生命设备数据")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        smDataApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取生命设备数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<SmDataDTO>> list(SmDataQuery query) {
        PageDTO<SmDataDTO> list = smDataApplicationService.getSmDatas(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取生命设备数据信息")
    @GetMapping("/{smDataId}")
    public ResponseDTO<SmDataDTO> info(@PathVariable(value = "smDataId", required = false) Long smDataId) {
        SmDataDTO smDataDTO = smDataApplicationService.getSmDataInfo(smDataId);
        return ResponseDTO.ok(smDataDTO);
    }

    @Operation(summary = "生命体征数据列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, SmDataQuery query) {
        PageDTO<SmDataDTO> list = smDataApplicationService.getSmDatas(query);
        CustomExcelUtil.writeToResponse(list.getRows(), SmDataDTO.class, response);
    }

    @PostMapping("/getSmDataHistory")
    public ResponseDTO<Object> getSmDataHistory(@RequestBody SmDataLuanShengQuiery query) {
        Object result = smDataApplicationService.getSmDataHistory(query);
        return ResponseDTO.ok(result);
    }

    @PostMapping("/getSmDataOnlineHistory")
    public ResponseDTO<Object> getSmDataOnlineHistory(@RequestBody SmDataLuanShengQuiery query) {
        Object result = smDataApplicationService.getSmDataOnlineHistory(query);
        return ResponseDTO.ok(result);
    }

}
