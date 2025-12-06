package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.domain.manage.kongTiaoData.dto.KongTiaoDataDTO;
import com.biology.domain.manage.kongTiaoDevice.KongTiaoDeviceApplicationService;
import com.biology.domain.manage.kongTiaoDevice.command.AddKongTiaoDeviceCommand;
import com.biology.domain.manage.kongTiaoDevice.command.UpdateKongTiaoDeviceCommand;
import com.biology.domain.manage.kongTiaoDevice.dto.KongTiaoDeviceDTO;
import com.biology.domain.manage.kongTiaoDevice.query.KongTiaoDeviceQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "知识库API", description = "知识库的增删查改")
@RestController
@RequestMapping("/manage/kongTiaoDevice")
@Validated
@RequiredArgsConstructor
public class KongTiaoDeviceController extends BaseController {
    private final KongTiaoDeviceApplicationService kongTiaoDeviceApplicationService;

    @Operation(summary = "添加空调设备")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddKongTiaoDeviceCommand command) {
        kongTiaoDeviceApplicationService.create(command);
        System.out.println("command================================: " + command.getArea());
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新空调设备")
    @PutMapping("/{kongTiaoDeviceId}")
    public ResponseDTO<Void> update(@PathVariable Long kongTiaoDeviceId,
            @RequestBody UpdateKongTiaoDeviceCommand command) {
        command.setKongTiaoDeviceId(kongTiaoDeviceId);
        kongTiaoDeviceApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除空调设备")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        kongTiaoDeviceApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取空调设备列表")
    @GetMapping
    public ResponseDTO<PageDTO<KongTiaoDeviceDTO>> list(KongTiaoDeviceQuery query) {
        PageDTO<KongTiaoDeviceDTO> list = kongTiaoDeviceApplicationService.getKongTiaoDevices(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取空调设备信息")
    @GetMapping("/{kongTiaoDeviceId}")
    public ResponseDTO<KongTiaoDeviceDTO> info(
            @PathVariable(value = "kongTiaoDeviceId", required = false) Long kongTiaoDeviceId) {
        KongTiaoDeviceDTO kongTiaoDeviceDTO = kongTiaoDeviceApplicationService.getKongTiaoDeviceInfo(kongTiaoDeviceId);
        return ResponseDTO.ok(kongTiaoDeviceDTO);
    }

    @Operation(summary = "空调设备列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, KongTiaoDeviceQuery query) {
        PageDTO<KongTiaoDeviceDTO> list = kongTiaoDeviceApplicationService.getKongTiaoDevices(query);
        CustomExcelUtil.writeToResponse(list.getRows(), KongTiaoDeviceDTO.class, response);
    }

    @GetMapping("/getKongTiaoDataFirst")
    public ResponseDTO<KongTiaoDataDTO> getKongTiaoDataFirst(@RequestParam String deviceSn) {
        KongTiaoDataDTO kongTiaoDataDTO = kongTiaoDeviceApplicationService.getKongTiaoDataFirst(deviceSn);
        return ResponseDTO.ok(kongTiaoDataDTO);
    }
}
