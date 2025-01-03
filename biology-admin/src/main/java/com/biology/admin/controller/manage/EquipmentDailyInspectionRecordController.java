package com.biology.admin.controller.manage;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Internal;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.equipment.EquipmentDailyInspectionRecordApplicationService;
import com.biology.domain.manage.equipment.command.AddEquipmentDailyInspectionRecordCommand;
import com.biology.domain.manage.equipment.command.ExcelEquipmentDailyInspectionRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentDailyInspectionRecordCommand;
import com.biology.domain.manage.equipment.dto.EquipmentDailyInspectionRecordDTO;
import com.biology.domain.manage.equipment.query.SearchEquipmentDailyInspectionRecordQuery;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Tag(name = "设备日常巡检记录API", description = "设备日常巡检记录的增删查改")
@RestController
@RequestMapping("/manage/equipment-daily-inspection")
@Validated
@RequiredArgsConstructor
public class EquipmentDailyInspectionRecordController extends BaseController {

    private final EquipmentDailyInspectionRecordApplicationService equipmentDailyInspectionRecordApplicationService;

    @Operation(summary = "添加设备日常巡检记录")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEquipmentDailyInspectionRecordCommand command) {
        equipmentDailyInspectionRecordApplicationService.createDailyInspectionRecord(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新设备日常巡检记录")
    @PutMapping("/{recordId}")
    public ResponseDTO<Void> update(@PathVariable Long recordId,
            @RequestBody UpdateEquipmentDailyInspectionRecordCommand command) {
        command.setRecordId(recordId);
        equipmentDailyInspectionRecordApplicationService.updateDailyInspectionRecord(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除设备日常巡检记录")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> recordIds) {
        equipmentDailyInspectionRecordApplicationService.deleteDailyInspectionRecords(recordIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取设备日常巡检记录详情")
    @GetMapping("/{recordId}")
    public ResponseDTO<EquipmentDailyInspectionRecordDTO> info(@PathVariable Long recordId) {
        EquipmentDailyInspectionRecordDTO recordDTO = equipmentDailyInspectionRecordApplicationService
                .getDailyInspectionRecordInfo(recordId);
        return ResponseDTO.ok(recordDTO);
    }

    @Operation(summary = "获取设备日常巡检记录列表")
    @GetMapping
    public ResponseDTO<PageDTO<EquipmentDailyInspectionRecordDTO>> list(
            SearchEquipmentDailyInspectionRecordQuery query) {
        PageDTO<EquipmentDailyInspectionRecordDTO> list = equipmentDailyInspectionRecordApplicationService
                .getDailyInspectionRecordList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "下载设备日常巡检记录导入模板")
    @GetMapping("/excelTemplate")
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new ExcelEquipmentDailyInspectionRecordCommand()),
                ExcelEquipmentDailyInspectionRecordCommand.class, response);
    }

    @Operation(summary = "导入设备日常巡检记录")
    @PostMapping("/excel")
    public ResponseDTO<Void> importDailyInspectionRecord(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
        }

        List<ExcelEquipmentDailyInspectionRecordCommand> excelCommands = CustomExcelUtil.readFromRequest(
                ExcelEquipmentDailyInspectionRecordCommand.class, file);

        List<AddEquipmentDailyInspectionRecordCommand> addCommands = new ArrayList<>();
        for (ExcelEquipmentDailyInspectionRecordCommand excelCommand : excelCommands) {
            AddEquipmentDailyInspectionRecordCommand addCommand = new AddEquipmentDailyInspectionRecordCommand();
            addCommand.setInspectionDate(excelCommand.getInspectionDate());
            addCommand.setInspectionCode(excelCommand.getInspectionCode());
            addCommand.setTaskDescription(excelCommand.getTaskDescription());
            addCommand.setInspector(excelCommand.getInspector());
            addCommand.setAnomalyCount(excelCommand.getAnomalyCount());
            addCommand.setAnomalyDescription(excelCommand.getAnomalyDescription());
            addCommands.add(addCommand);
        }

        for (AddEquipmentDailyInspectionRecordCommand command : addCommands) {
            equipmentDailyInspectionRecordApplicationService.createDailyInspectionRecord(command);
        }

        return ResponseDTO.ok();
    }
}