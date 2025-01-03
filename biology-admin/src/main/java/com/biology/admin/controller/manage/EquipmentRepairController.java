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
import com.biology.domain.manage.equipment.EquipmentRepairRecordApplicationService;
import com.biology.domain.manage.equipment.command.AddEquipmentRepairRecordCommand;
import com.biology.domain.manage.equipment.command.ExcelEquipmentRepairRecordCommand;
import com.biology.domain.manage.equipment.command.UpdateEquipmentRepairRecordCommand;
import com.biology.domain.manage.equipment.dto.EquipmentRepairRecordDTO;
import com.biology.domain.manage.equipment.model.EquipmentFactory;
import com.biology.domain.manage.equipment.model.EquipmentModel;
import com.biology.domain.manage.equipment.query.EquipmentDataEchartDTO;
import com.biology.domain.manage.equipment.query.SearchEquipmentRepairQuery;
import com.biology.domain.manage.event.dto.EventEchartDTO;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Tag(name = "设备维修记录API", description = "设备维修记录的增删查改")
@RestController
@RequestMapping("/manage/equipment-repair")
@Validated
@RequiredArgsConstructor
public class EquipmentRepairController extends BaseController {

    private final EquipmentRepairRecordApplicationService equipmentRepairRecordApplicationService;
    private final EquipmentFactory equipmentFactory;

    @Operation(summary = "添加设备维修记录")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEquipmentRepairRecordCommand command) {
        equipmentRepairRecordApplicationService.createRepairRecord(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新设备维修记录")
    @PutMapping("/{recordId}")
    public ResponseDTO<Void> update(@PathVariable Long recordId,
            @RequestBody UpdateEquipmentRepairRecordCommand command) {
        command.setRecordId(recordId);
        equipmentRepairRecordApplicationService.updateRepairRecord(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除设备维修记录")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> recordIds) {
        equipmentRepairRecordApplicationService.deleteRepairRecords(recordIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取设备维修记录信息")
    @GetMapping("/{recordId}")
    public ResponseDTO<EquipmentRepairRecordDTO> info(@PathVariable Long recordId) {
        EquipmentRepairRecordDTO repairDTO = equipmentRepairRecordApplicationService.getRepairRecordInfo(recordId);
        return ResponseDTO.ok(repairDTO);
    }

    @Operation(summary = "获取设备维修记录列表")
    @GetMapping
    public ResponseDTO<PageDTO<EquipmentRepairRecordDTO>> list(SearchEquipmentRepairQuery query) {
        PageDTO<EquipmentRepairRecordDTO> list = equipmentRepairRecordApplicationService.getRepairRecordList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "下载设备维修记录导入模板")
    @GetMapping("/excelTemplate")
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new ExcelEquipmentRepairRecordCommand()),
                ExcelEquipmentRepairRecordCommand.class, response);
    }

    @Operation(summary = "导入设备维修记录")
    @PostMapping("/excel")
    public ResponseDTO<Void> importRepairRecord(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
        }

        List<ExcelEquipmentRepairRecordCommand> excelCommands = CustomExcelUtil.readFromRequest(
                ExcelEquipmentRepairRecordCommand.class, file);

        List<AddEquipmentRepairRecordCommand> addCommands = new ArrayList<>();
        for (ExcelEquipmentRepairRecordCommand excelCommand : excelCommands) {
            AddEquipmentRepairRecordCommand addCommand = new AddEquipmentRepairRecordCommand();
            // addCommand.setEquipmentCode(excelCommand.getEquipmentCode());
            addCommand.setRepairCode(excelCommand.getRepairCode());

            // 根据code查询设备ID
            EquipmentModel equipmentModel = equipmentFactory.loadByCode(excelCommand.getEquipmentCode());
            addCommand.setEquipmentId(equipmentModel.getEquipmentId());

            addCommand.setRepairDate(excelCommand.getRepairDate());
            addCommand.setRepairContent(excelCommand.getRepairContent());
            addCommand.setFaultReason(excelCommand.getFaultReason());
            addCommand.setRepairPersonnel(excelCommand.getRepairPersonnel());
            addCommand.setRepairCost(excelCommand.getRepairCost());
            addCommand.setRepairResult(excelCommand.getRepairResult());
            addCommands.add(addCommand);
        }

        for (AddEquipmentRepairRecordCommand command : addCommands) {
            equipmentRepairRecordApplicationService.createRepairRecord(command);
        }

        return ResponseDTO.ok();
    }

    // 维修记录 按照时间统计 - 年/月/周
    @Operation(summary = "维修记录 按照时间统计")
    @GetMapping("/repair-record-count-by-time")
    public ResponseDTO<EventEchartDTO> repairRecordCountByTime(
            EquipmentDataEchartDTO query) {
        // 参数校验
        // if (!query.getDayType().equals("week") &&
        // !query.getDayType().equals("month")) {
        // return ResponseDTO.fail(new ApiException(Internal.GET_ENUM_FAILED));
        // }

        // 调用service获取统计数据
        EventEchartDTO eventEchartDTO = equipmentRepairRecordApplicationService.repairRecordByTime(query);

        return ResponseDTO.ok(eventEchartDTO);
    }
}