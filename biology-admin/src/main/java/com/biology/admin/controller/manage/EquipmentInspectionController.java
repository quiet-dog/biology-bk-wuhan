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
import com.biology.domain.manage.equipment.EquipmentInspectionRecordApplicationService;
import com.biology.domain.manage.equipment.command.AddEquipmentInspectionRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentInspectionRecordCommand;
import com.biology.domain.manage.equipment.dto.EquipmentInspectionRecordDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentInspectionQuery;
import com.biology.domain.manage.equipment.command.ExcelEquipmentInspectionRecordCommand;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Tag(name = "设备检修记录API", description = "设备检修记录的增删查改")
@RestController
@RequestMapping("/manage/equipment-inspection")
@Validated
@RequiredArgsConstructor
public class EquipmentInspectionController extends BaseController {

    private final EquipmentInspectionRecordApplicationService equipmentInspectionRecordApplicationService;
    private final EquipmentFactory equipmentFactory;

    @Operation(summary = "添加设备检修记录")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEquipmentInspectionRecordCommand command) {
        equipmentInspectionRecordApplicationService.createInspectionRecord(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新设备检修记录")
    @PutMapping("/{recordId}")
    public ResponseDTO<Void> update(@PathVariable Long recordId,
            @RequestBody UpdateEquipmentInspectionRecordCommand command) {
        command.setRecordId(recordId);
        equipmentInspectionRecordApplicationService.updateInspectionRecord(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除设备检修记录")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> recordIds) {
        equipmentInspectionRecordApplicationService.deleteInspectionRecords(recordIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取设备检修记录信息")
    @GetMapping("/{recordId}")
    public ResponseDTO<EquipmentInspectionRecordDTO> info(@PathVariable Long recordId) {
        EquipmentInspectionRecordDTO inspectionDTO = equipmentInspectionRecordApplicationService
                .getInspectionRecordInfo(recordId);
        return ResponseDTO.ok(inspectionDTO);
    }

    @Operation(summary = "获取设备检修记录列表")
    @GetMapping
    public ResponseDTO<PageDTO<EquipmentInspectionRecordDTO>> list(SearchEquipmentInspectionQuery query) {
        PageDTO<EquipmentInspectionRecordDTO> list = equipmentInspectionRecordApplicationService
                .getInspectionRecordList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "下载设备检修记录导入模板")
    @GetMapping("/excelTemplate")
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new ExcelEquipmentInspectionRecordCommand()),
                ExcelEquipmentInspectionRecordCommand.class, response);
    }

    @Operation(summary = "导入设备检修记录")
    @PostMapping("/excel")
    public ResponseDTO<Void> importInspectionRecord(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
        }

        List<ExcelEquipmentInspectionRecordCommand> excelCommands = CustomExcelUtil.readFromRequest(
                ExcelEquipmentInspectionRecordCommand.class, file);

        List<AddEquipmentInspectionRecordCommand> addCommands = new ArrayList<>();
        for (ExcelEquipmentInspectionRecordCommand excelCommand : excelCommands) {
            AddEquipmentInspectionRecordCommand addCommand = new AddEquipmentInspectionRecordCommand();

            // 根据设备编号查询设备ID
            EquipmentModel equipmentModel = equipmentFactory.loadByCode(excelCommand.getEquipmentCode());
            addCommand.setEquipmentId(equipmentModel.getEquipmentId());

            addCommand.setInspectionCode(excelCommand.getInspectionCode());
            addCommand.setInspectionDate(excelCommand.getInspectionDate());
            addCommand.setInspectionContent(excelCommand.getInspectionContent());
            addCommand.setInspector(excelCommand.getInspector());
            addCommand.setFaultReason(excelCommand.getFaultReason());
            addCommands.add(addCommand);
        }

        for (AddEquipmentInspectionRecordCommand command : addCommands) {
            equipmentInspectionRecordApplicationService.createInspectionRecord(command);
        }

        return ResponseDTO.ok();
    }

}