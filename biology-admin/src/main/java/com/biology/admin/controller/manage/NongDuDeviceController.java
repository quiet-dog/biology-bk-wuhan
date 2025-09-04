package com.biology.admin.controller.manage;

import java.util.List;
import java.util.Map;

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

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.nongDuDevice.NongDuDeviceApplicationService;
import com.biology.domain.manage.nongDuDevice.command.AddNongDuDeviceCommand;
import com.biology.domain.manage.nongDuDevice.command.UpdateNongDuDeviceCommand;
import com.biology.domain.manage.nongDuDevice.dto.NongDuDeviceDTO;
import com.biology.domain.manage.nongDuDevice.query.NongDuDeviceQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "采样设备", description = "采样设备的增删查改")
@RestController
@RequestMapping("/manage/nongDuDevice")
@Validated
@RequiredArgsConstructor
public class NongDuDeviceController extends BaseController {
    private final NongDuDeviceApplicationService nongDuDeviceApplicationService;

    @Operation(summary = "添加采样设备")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddNongDuDeviceCommand command) {
        nongDuDeviceApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新采样设备")
    @PutMapping("/{nongDuDeviceId}")
    public ResponseDTO<Void> update(@PathVariable Long nongDuDeviceId, @RequestBody UpdateNongDuDeviceCommand command) {
        command.setNongDuDeviceId(nongDuDeviceId);
        nongDuDeviceApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除采样设备")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        nongDuDeviceApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取采样设备列表")
    @GetMapping
    public ResponseDTO<PageDTO<NongDuDeviceDTO>> list(NongDuDeviceQuery query) {
        PageDTO<NongDuDeviceDTO> list = nongDuDeviceApplicationService.getNongDuDevices(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取采样设备信息")
    @GetMapping("/{nongDuDeviceId}")
    public ResponseDTO<NongDuDeviceDTO> info(
            @PathVariable(value = "nongDuDeviceId", required = false) Long nongDuDeviceId) {
        NongDuDeviceDTO nongDuDeviceDTO = nongDuDeviceApplicationService.getNongDuDeviceInfo(nongDuDeviceId);
        return ResponseDTO.ok(nongDuDeviceDTO);
    }

    @Operation(summary = "采样设备列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, NongDuDeviceQuery query) {
        PageDTO<NongDuDeviceDTO> list = nongDuDeviceApplicationService.getNongDuDevices(query);
        CustomExcelUtil.writeToResponse(list.getRows(), NongDuDeviceDTO.class, response);
    }

    @GetMapping("/getOnlineCount")
    public ResponseDTO<Map<String, Object>> getOnlineCount() {
        return ResponseDTO.ok(nongDuDeviceApplicationService.getOnlineCount());
    }
}
