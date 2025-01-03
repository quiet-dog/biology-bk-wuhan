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
import com.biology.domain.manage.craftarchive.command.AddCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.command.ExcelCraftArchiveCommand;
import com.biology.domain.manage.equipment.EquipmentMaintenanceManualApplicationService;
import com.biology.domain.manage.equipment.command.AddEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.command.ExcelEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.dto.EquipmentMaintenanceManualDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentMaintenanceManualQuery;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Tag(name = "设备维修手册API", description = "设备维修手册的增删查改")
@RestController
@RequestMapping("/manage/equipment-maintenance-manual")
@Validated
@RequiredArgsConstructor
public class EquipmentMaintenanceManualController extends BaseController {

    private final EquipmentMaintenanceManualApplicationService equipmentMaintenanceManualApplicationService;

    private final EquipmentFactory equipmentFactory;

    @Operation(summary = "添加设备维修手册")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEquipmentMaintenanceManualCommand command) {
        equipmentMaintenanceManualApplicationService.createMaintenanceManual(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新设备维修手册")
    @PutMapping("/{manualId}")
    public ResponseDTO<Void> update(@PathVariable Long manualId, @RequestBody UpdateEquipmentMaintenanceManualCommand command) {
        command.setManualId(manualId);
        equipmentMaintenanceManualApplicationService.updateMaintenanceManual(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除设备维修手册")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> manualIds) {
        equipmentMaintenanceManualApplicationService.deleteMaintenanceManuals(manualIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取设备维修手册信息")
    @GetMapping("/{manualId}")
    public ResponseDTO<EquipmentMaintenanceManualDTO> info(@PathVariable Long manualId) {
        EquipmentMaintenanceManualDTO manualDTO = equipmentMaintenanceManualApplicationService.getMaintenanceManualInfo(manualId);
        return ResponseDTO.ok(manualDTO);
    }

    @Operation(summary = "获取设备维修手册列表")
    @GetMapping
    public ResponseDTO<PageDTO<EquipmentMaintenanceManualDTO>> list(SearchEquipmentMaintenanceManualQuery query) {
        PageDTO<EquipmentMaintenanceManualDTO> list = equipmentMaintenanceManualApplicationService.getMaintenanceManualList(query);
        return ResponseDTO.ok(list);
    }


    @Operation(summary = "下载设备维修手册导入模板")
    @GetMapping("/excelTemplate")
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new ExcelEquipmentMaintenanceManualCommand()), ExcelEquipmentMaintenanceManualCommand.class,
                response);
    }

    // 导入设备维修手册
    @Operation(summary = "导入设备维修手册")
    @PostMapping("/excel")
    public ResponseDTO<String> importEquipmentMaintenanceManual(MultipartFile file) {
        // TODO: 实现导入设备维修手册的功能
        if (file.isEmpty()) {
            return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
        }

        // 读取Excel数据到ExcelCommand对象列表
        List<ExcelEquipmentMaintenanceManualCommand> excelCommands = CustomExcelUtil.readFromRequest(
                ExcelEquipmentMaintenanceManualCommand.class, file);

        // 转换成添加命令对象列表
        List<AddEquipmentMaintenanceManualCommand> addCommands = new ArrayList<>();
        for (ExcelEquipmentMaintenanceManualCommand excelCommand : excelCommands) {
            AddEquipmentMaintenanceManualCommand addCommand = new AddEquipmentMaintenanceManualCommand();
            // 根据设备编号查询设备ID
            EquipmentModel equipmentModel = equipmentFactory.loadByCode(excelCommand.getEquipmentCode());
            // TODO: 根据Excel数据设置addCommand的属性
            addCommand.setEquipmentId(equipmentModel.getEquipmentId());
            addCommand.setManualVersion(excelCommand.getManualVersion());
            addCommands.add(addCommand);
        }

        // 批量处理导入
        for (AddEquipmentMaintenanceManualCommand command : addCommands) {
            equipmentMaintenanceManualApplicationService.createMaintenanceManual(command);
        }

        return ResponseDTO.ok();
    }
} 
