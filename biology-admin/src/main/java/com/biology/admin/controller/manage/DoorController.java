package com.biology.admin.controller.manage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.converters.localdatetime.LocalDateTimeDateConverter;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Internal;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.alarm.command.AddAlarmCommand;
import com.biology.domain.manage.detection.DetectionApplicationService;
import com.biology.domain.manage.door.DoorApplicationService;
import com.biology.domain.manage.door.command.AddDoorCommand;
import com.biology.domain.manage.door.command.ExcelDoor;
import com.biology.domain.manage.door.command.UpdateDoorCommand;
import com.biology.domain.manage.door.dto.DoorDTO;
import com.biology.domain.manage.door.query.DoorQuery;
import com.biology.domain.manage.equipment.command.ExcelEquipmentCommand;
import com.biology.domain.manage.koala.db.KoalaService;
import com.biology.domain.manage.koala.dto.auth.LoginResDTO;
import com.biology.domain.manage.koala.dto.records.KoalaRecordResponseDTO;
import com.biology.domain.manage.koala.dto.records.KoalaRecordsQuery;
import com.biology.domain.manage.threshold.ThresholdApplicationService;
import com.biology.domain.manage.threshold.db.ThresholdValueService;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "门禁事件API", description = "门禁事件的增删查改")
@RestController
@RequestMapping("/manage/door")
@Validated
@RequiredArgsConstructor
public class DoorController extends BaseController {
    private final DoorApplicationService doorApplicationService;

    private final KoalaService koalaService;

    @Operation(summary = "添加门禁事件")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddDoorCommand command) {
        doorApplicationService.createDoor(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新门禁事件")
    @PutMapping("/{doorId}")
    public ResponseDTO<Void> update(@PathVariable Long doorId, @RequestBody UpdateDoorCommand command) {
        command.setDoorId(doorId);
        doorApplicationService.updateDoor(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除门禁事件")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> doorIds) {
        doorApplicationService.deleteEmergencies(doorIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取门禁事件详情")
    @GetMapping("/{doorId}")
    public ResponseDTO<DoorDTO> getDoorInfo(
            @PathVariable(value = "doorId", required = false) Long doorId) {
        DoorDTO doorDetailDTO = doorApplicationService.getDoorInfo(doorId);
        return ResponseDTO.ok(doorDetailDTO);
    }

    @Operation(summary = "获取门禁事件列表")
    @GetMapping
    public ResponseDTO<PageDTO<DoorDTO>> list(DoorQuery query) {
        PageDTO<DoorDTO> list = doorApplicationService.getDoorList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取当天门禁出入统计")
    @GetMapping("/enter")
    public ResponseDTO<Double> getDayStatus() {
        return ResponseDTO.ok(doorApplicationService.getDayStatus());
    }

    @Operation(summary = "门禁记录测试")
    @GetMapping("/records")
    public ResponseDTO<KoalaRecordResponseDTO> getRecords(KoalaRecordsQuery query) {
        return ResponseDTO.ok(koalaService.getRecords(query));
    }

    @Operation(summary = "门禁记录测试")
    @GetMapping("/auth")
    public ResponseDTO<LoginResDTO> auth() {
        return ResponseDTO.ok(koalaService.auth());
    }

    @Operation(summary = "导入出勤记录")
    @PostMapping("/excel")
    public ResponseDTO<Void> importDoor(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
        }

        // 读取Excel数据到ExcelCommand对象列表
        List<ExcelDoor> excelCommands = CustomExcelUtil.readFromRequest(
                ExcelDoor.class, file);
        // 转换成添加命令对象列表
        List<UpdateDoorCommand> addDoorCommands = excelCommands.stream()
                .map(ExcelDoor::toUpdateDoorCommand)
                .collect(Collectors.toList());
        for (UpdateDoorCommand command : addDoorCommands) {
            doorApplicationService.updateDoor(command);
        }

        return ResponseDTO.ok();
    }

    @Operation(summary = "下载出勤记录模板")
    @GetMapping("/excelTemplate")
    public void downloadTemplate(HttpServletResponse response) {
        ExcelDoor eDoor = new ExcelDoor();
        LocalDateTime now = LocalDateTime.now();
        eDoor.setChuQinTime(now);
        eDoor.setName("xxx");
        eDoor.setCode("人员档案的档案编号");
        ArrayList<ExcelDoor> aDoors = ListUtil.toList(new ExcelDoor());
        aDoors.add(eDoor);
        CustomExcelUtil.writeToResponse(aDoors, ExcelDoor.class,
                response);
    }

}
