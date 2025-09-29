package com.biology.admin.controller.manage;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.common.exception.error.ErrorCode.Internal;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.equipment.EquipmentApplicationService;
import com.biology.domain.manage.equipment.command.AddEquipmentCommand;
import com.biology.domain.manage.equipment.command.AddEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.command.ExcelEquipmentCommand;
import com.biology.domain.manage.equipment.command.ExcelEquipmentMaintenanceManualCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentCommand;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDetailDTO;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.SearchEquipmentQuery;
import com.biology.domain.manage.event.dto.EventDTO;
import com.biology.domain.manage.event.query.EventSearch;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Tag(name = "设备档案API", description = "设备档案的增删查改")
@RestController
@RequestMapping("/manage/equipment")
@Validated
@RequiredArgsConstructor
public class EquipmentController extends BaseController {

    private final EquipmentApplicationService equipmentApplicationService;

    @Operation(summary = "添加设备档案")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEquipmentCommand command) {
        equipmentApplicationService.createEquipment(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新设备档案")
    @PutMapping("/{equipmentId}")
    public ResponseDTO<Void> update(@PathVariable Long equipmentId, @RequestBody UpdateEquipmentCommand command) {
        command.setEquipmentId(equipmentId);
        equipmentApplicationService.updateEquipment(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除设备档案")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> equipmentIds) {
        equipmentApplicationService.deleteEquipments(equipmentIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取设备档案信息")
    @GetMapping("/{equipmentId}")
    public ResponseDTO<EquipmentDTO> info(@PathVariable Long equipmentId) {
        EquipmentDTO equipmentDTO = equipmentApplicationService.getEquipmentInfo(equipmentId);
        return ResponseDTO.ok(equipmentDTO);
    }

    @Operation(summary = "获取设备档案列表")
    @GetMapping
    public ResponseDTO<PageDTO<EquipmentDTO>> list(SearchEquipmentQuery query) {
        PageDTO<EquipmentDTO> list = equipmentApplicationService.getEquipmentList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取设备档案列表")
    @GetMapping("/detail")
    public ResponseDTO<PageDTO<EquipmentDetailDTO>> detailList(SearchEquipmentQuery query) {
        PageDTO<EquipmentDetailDTO> list = equipmentApplicationService.getEquipmentDetailList(query);
        return ResponseDTO.ok(list);
    }

    // 下载设备档案模板
    @Operation(summary = "下载设备档案模板")
    @GetMapping("/template")
    public ResponseDTO<Void> downloadTemplate(HttpServletResponse response) {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new ExcelEquipmentCommand()), ExcelEquipmentCommand.class,
                response);
        return ResponseDTO.ok();
    }

    // 导入设备档案
    @Operation(summary = "导入设备档案")
    @PostMapping("/excel")
    public ResponseDTO<String> importEquipmentMaintenanceManual(MultipartFile file) {
        // TODO: 实现导入设备维修手册的功能
        if (file.isEmpty()) {
            return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
        }

        // 读取Excel数据到ExcelCommand对象列表
        List<ExcelEquipmentCommand> excelCommands = CustomExcelUtil.readFromRequest(
                ExcelEquipmentCommand.class, file);

        // 转换成添加命令对象列表
        List<AddEquipmentCommand> addCommands = new ArrayList<>();
        for (ExcelEquipmentCommand excelCommand : excelCommands) {
            AddEquipmentCommand addCommand = new AddEquipmentCommand();

            // 校验
            if (StrUtil.isBlank(excelCommand.getEquipmentCode())) {
                return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
            }

            if (StrUtil.isBlank(excelCommand.getEquipmentName())) {
                return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
            }

            if (StrUtil.isBlank(excelCommand.getEquipmentName())) {
                return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
            }

            if (StrUtil.isBlank(excelCommand.getEquipmentType())) {
                return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
            }

            addCommand.setEquipmentCode(excelCommand.getEquipmentCode());
            addCommand.setEquipmentName(excelCommand.getEquipmentName());
            addCommand.setEquipmentType(excelCommand.getEquipmentType());
            addCommand.setManufacturer(excelCommand.getManufacturer());
            addCommand.setPurchaseDate(excelCommand.getPurchaseDate());
            addCommand.setInstallationLocation(excelCommand.getInstallationLocation());
            addCommand.setRoomNumber(excelCommand.getRoomNumber());
            addCommand.setBiosafetydataName(excelCommand.getBiosafetydataName());
            addCommand.setTechnicalSpec(excelCommand.getTechnicalSpec());
            addCommand.setPerformanceParams(excelCommand.getPerformanceParams());
            // addCommand.setUsageStatus(excelCommand.getUsageStatus());
            addCommands.add(addCommand);
        }

        // 批量处理导入
        Boolean isFaild = false;
        for (AddEquipmentCommand command : addCommands) {
            try {
                equipmentApplicationService.createEquipment(command);
            } catch (ApiException e) {
                isFaild = true;
            }
        }
        if (isFaild) {
            return ResponseDTO.fail(new ApiException(Business.EQUIPMENT_CODE_ONE_EXISTS));
        }

        return ResponseDTO.ok();
    }

    @Operation(summary = "设备档案列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, SearchEquipmentQuery query) {
        PageDTO<EquipmentDTO> userList = equipmentApplicationService.getEquipmentList(query);
        CustomExcelUtil.writeToResponse(userList.getRows(), EquipmentDTO.class, response);
    }

    @Operation(summary = "获取设备档案报警统计")
    @GetMapping("/alarmCount")
    public ResponseDTO<Long> getAlarmCount(@RequestParam String dayType) {
        Long alarmCount = equipmentApplicationService.getAlarmCount(dayType);
        return ResponseDTO.ok(alarmCount);
    }
}
