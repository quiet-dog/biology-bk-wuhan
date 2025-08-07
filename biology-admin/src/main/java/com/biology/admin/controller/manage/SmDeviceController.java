package com.biology.admin.controller.manage;

import java.util.List;

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
import com.biology.domain.manage.smDevice.command.AddSmDeviceCommand;
import com.biology.domain.manage.smDevice.command.UpdateSmDeviceCommand;
import com.biology.domain.manage.smDevice.dto.SmDeviceDTO;
import com.biology.domain.manage.smDevice.query.SmDeviceQuery;
import com.biology.domain.manage.smDevice.SmDeviceApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "生命设备", description = "生命设备的增删查改")
@RestController
@RequestMapping("/manage/smDevice")
@Validated
@RequiredArgsConstructor
public class SmDeviceController extends BaseController {
    private final SmDeviceApplicationService smDeviceApplicationService;

    @Operation(summary = "添加生命设备")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddSmDeviceCommand command) {
        smDeviceApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新生命设备")
    @PostMapping("/{smDeviceId}")
    public ResponseDTO<Void> update(@RequestBody UpdateSmDeviceCommand command) {
        smDeviceApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除生命设备")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        smDeviceApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取生命设备列表")
    @GetMapping
    public ResponseDTO<PageDTO<SmDeviceDTO>> list(SmDeviceQuery query) {
        PageDTO<SmDeviceDTO> list = smDeviceApplicationService.getSmDevices(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取生命设备信息")
    @GetMapping("/{smDeviceId}")
    public ResponseDTO<SmDeviceDTO> info(@PathVariable(value = "smDeviceId", required = false) Long smDeviceId) {
        SmDeviceDTO smDeviceDTO = smDeviceApplicationService.getSmDeviceInfo(smDeviceId);
        return ResponseDTO.ok(smDeviceDTO);
    }
}
