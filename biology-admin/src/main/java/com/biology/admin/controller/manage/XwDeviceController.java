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
import com.biology.domain.manage.xwDevice.XwDeviceApplicationService;
import com.biology.domain.manage.xwDevice.command.AddXwDeviceCommand;
import com.biology.domain.manage.xwDevice.command.UpdateXwDeviceCommand;
import com.biology.domain.manage.xwDevice.dto.XwDeviceDTO;
import com.biology.domain.manage.xwDevice.query.XwDeviceQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "行为设备", description = "行为设备的增删查改")
@RestController
@RequestMapping("/manage/xwDevice")
@Validated
@RequiredArgsConstructor
public class XwDeviceController extends BaseController {
    private final XwDeviceApplicationService xwDeviceApplicationService;

    @Operation(summary = "添加行为设备")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddXwDeviceCommand command) {
        xwDeviceApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新行为设备")
    @PostMapping("/{xwDeviceId}")
    public ResponseDTO<Void> update(@RequestBody UpdateXwDeviceCommand command) {
        xwDeviceApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除行为设备")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        xwDeviceApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取行为设备列表")
    @GetMapping
    public ResponseDTO<PageDTO<XwDeviceDTO>> list(XwDeviceQuery query) {
        PageDTO<XwDeviceDTO> list = xwDeviceApplicationService.getXwDevices(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取行为设备信息")
    @GetMapping("/{xwDeviceId}")
    public ResponseDTO<XwDeviceDTO> info(@PathVariable(value = "xwDeviceId", required = false) Long xwDeviceId) {
        XwDeviceDTO xwDeviceDTO = xwDeviceApplicationService.getXwDeviceInfo(xwDeviceId);
        return ResponseDTO.ok(xwDeviceDTO);
    }
}
