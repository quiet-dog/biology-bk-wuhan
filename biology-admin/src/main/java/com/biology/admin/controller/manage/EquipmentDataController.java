package com.biology.admin.controller.manage;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.biology.common.core.page.PageDTO;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.equipment.EquipmentDataApplicationService;
import com.biology.domain.manage.equipment.command.AddEquipmentDataCommand;
// import com.biology.domain.manage.equipment.command.ExcelEquipmentDataCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentDataCommand;
import com.biology.domain.manage.equipment.dto.EquipmentDataDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;
import com.biology.domain.manage.equipment.dto.TotalTimeDTO;
import com.biology.domain.manage.equipment.query.EquipmentDataEchartDTO;
import com.biology.domain.manage.equipment.query.SearchEquipmentDataQuery;
import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Tag(name = "设备数据管理")
@RestController
@RequestMapping("/manage/equipment-data")
@Validated
@RequiredArgsConstructor
public class EquipmentDataController extends BaseController {

    private final EquipmentDataApplicationService equipmentDataApplicationService;

    @Operation(summary = "创建设备数据")
    @PostMapping
    public ResponseDTO<Void> create(@RequestBody AddEquipmentDataCommand command) {
        equipmentDataApplicationService.createEquipmentData(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新设备数据")
    @PutMapping
    public ResponseDTO<Void> update(@RequestBody UpdateEquipmentDataCommand command) {
        equipmentDataApplicationService.updateEquipmentData(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除设备数据")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestBody List<Long> equipmentIds) {
        equipmentDataApplicationService.deleteEquipmentData(equipmentIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取设备数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<EquipmentDataDTO>> list(SearchEquipmentDataQuery query) {
        PageDTO<EquipmentDataDTO> list = equipmentDataApplicationService.getEquipmentDataList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取设备数据详情")
    @GetMapping("/{equipmentId}")
    public ResponseDTO<EquipmentDataDTO> getInfo(@PathVariable Long equipmentId) {
        EquipmentDataDTO info = equipmentDataApplicationService.getEquipmentDataInfo(equipmentId);
        return ResponseDTO.ok(info);
    }

    @Operation(summary = "获取设备数据历史统计当天详情")
    @GetMapping("/history")
    public EquipmentDataStockEchartDTO getEquipmentDataStockDay(EquipmentDataEchartDTO query) {
        return equipmentDataApplicationService.getEquipmentDataStockDay(query.getThresholdId());
    }

    @Operation(summary = "获取设备数据历史统计当天详情")
    @GetMapping("/totalTime")
    public ResponseDTO<TotalTimeDTO> getTotalTime(Long equipmentId) {
        return ResponseDTO.ok(equipmentDataApplicationService.getTotalTime(equipmentId));
    }

    // @Operation(summary = "下载设备数据导入模板")
    // @GetMapping("/excelTemplate")
    // public void downloadImportTemplate(HttpServletResponse response) throws
    // IOException {
    // CustomExcelUtil.writeToResponse(ListUtil.toList(new
    // ExcelEquipmentDataCommand()), ExcelEquipmentDataCommand.class,
    // response);
    // }

    // @Operation(summary = "导入设备数据")
    // @PostMapping("/import")
    // public ResponseDTO<Void> importData(@RequestParam("file") MultipartFile file)
    // throws IOException {
    // List<ExcelEquipmentDataCommand> dataList =
    // CustomExcelUtil.readFromRequest(file, ExcelEquipmentDataCommand.class);
    // equipmentDataApplicationService.importEquipmentData(dataList);
    // return ResponseDTO.ok();
    // }

    // @Operation(summary = "导出设备数据")
    // @GetMapping("/export")
    // public void export(SearchEquipmentDataQuery query, HttpServletResponse
    // response) throws IOException {
    // List<ExcelEquipmentDataCommand> dataList =
    // equipmentDataApplicationService.exportEquipmentData(query);
    // CustomExcelUtil.writeToResponse(dataList, ExcelEquipmentDataCommand.class,
    // response);
    // }
}
