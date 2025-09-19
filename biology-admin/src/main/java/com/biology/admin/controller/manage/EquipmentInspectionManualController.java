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
import com.biology.domain.manage.equipment.EquipmentInspectionManualApplicationService;
import com.biology.domain.manage.equipment.command.AddEquipmentInspectionManualCommand;
import com.biology.domain.manage.equipment.command.AddEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.command.ExcelEquipmentInspectionManualCommand;
import com.biology.domain.manage.equipment.command.ExcelEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentInspectionManualCommand;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.dto.EquipmentInspectionManualDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentInspectionManualQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.ListUtil;

@Tag(name = "设备检修手册API", description = "设备检修手册的增删查改")
@RestController
@RequestMapping("/manage/equipment-inspection-manual")
@Validated
@RequiredArgsConstructor
public class EquipmentInspectionManualController extends BaseController {

    private final EquipmentInspectionManualApplicationService equipmentInspectionManualApplicationService;

    private final EquipmentFactory equipmentFactory;

    @Operation(summary = "添加设备检修手册")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEquipmentInspectionManualCommand command) {
        equipmentInspectionManualApplicationService.createInspectionManual(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新设备检修手册")
    @PutMapping("/{manualId}")
    public ResponseDTO<Void> update(@PathVariable Long manualId,
            @RequestBody UpdateEquipmentInspectionManualCommand command) {
        command.setManualId(manualId);
        equipmentInspectionManualApplicationService.updateInspectionManual(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除设备检修手册")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> manualIds) {
        equipmentInspectionManualApplicationService.deleteInspectionManuals(manualIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取设备检修手册信息")
    @GetMapping("/{manualId}")
    public ResponseDTO<EquipmentInspectionManualDTO> info(@PathVariable Long manualId) {
        EquipmentInspectionManualDTO manualDTO = equipmentInspectionManualApplicationService
                .getInspectionManualInfo(manualId);
        return ResponseDTO.ok(manualDTO);
    }

    @Operation(summary = "获取设备检修手册列表")
    @GetMapping
    public ResponseDTO<PageDTO<EquipmentInspectionManualDTO>> list(SearchEquipmentInspectionManualQuery query) {
        PageDTO<EquipmentInspectionManualDTO> list = equipmentInspectionManualApplicationService
                .getInspectionManualList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "下载设备检修手册导入模板")
    @GetMapping("/excelTemplate")
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new ExcelEquipmentInspectionManualCommand()),
                ExcelEquipmentInspectionManualCommand.class,
                response);
    }

    @Operation(summary = "开关启用状态")
    @PutMapping("/{manualId}/toggle-enable-status")
    public ResponseDTO<Void> toggleEnableStatus(@PathVariable Long manualId, @RequestParam Boolean isEnabled) {
        equipmentInspectionManualApplicationService.toggleEnableStatus(manualId, isEnabled);
        return ResponseDTO.ok();
    }

    // 导入
    @Operation(summary = "导入设备检修手册")
    @PostMapping("/excel")
    public ResponseDTO<Void> importInspectionManual(@RequestParam("file") MultipartFile file) {
        // TODO: 实现导入设备检修手册的功能
        if (file.isEmpty()) {
            return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
        }

        // 读取Excel数据到ExcelCommand对象列表
        List<ExcelEquipmentInspectionManualCommand> excelCommands = CustomExcelUtil.readFromRequest(
                ExcelEquipmentInspectionManualCommand.class, file);

        // 转换成添加命令对象列表
        List<AddEquipmentInspectionManualCommand> addCommands = new ArrayList<>();
        for (ExcelEquipmentInspectionManualCommand excelCommand : excelCommands) {
            AddEquipmentInspectionManualCommand addCommand = new AddEquipmentInspectionManualCommand();

            // 根据设备编号查询设备ID
            EquipmentModel equipmentModel = equipmentFactory.loadByCode(excelCommand.getEquipmentCode());
            // TODO: 根据Excel数据设置addCommand的属性
            addCommand.setEquipmentId(equipmentModel.getEquipmentId());
            addCommand.setManualVersion(excelCommand.getManualVersion());
            addCommand.setSuitableScope(excelCommand.getSuitableScope());
            addCommands.add(addCommand);
        }

        // 批量处理导入
        for (AddEquipmentInspectionManualCommand command : addCommands) {
            equipmentInspectionManualApplicationService.createInspectionManual(command);
        }

        return ResponseDTO.ok();
    }

}