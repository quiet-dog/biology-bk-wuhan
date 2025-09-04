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

import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.jianCeDevice.JianCeDeviceApplicationService;
import com.biology.domain.manage.jianCeDevice.command.AddJianCeDeviceCommand;
import com.biology.domain.manage.jianCeDevice.command.UpdateJianCeDeviceCommand;
import com.biology.domain.manage.jianCeDevice.dto.JianCeDeviceDTO;
import com.biology.domain.manage.jianCeDevice.query.JianCeDeviceQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "监测设备", description = "监测设备的增删查改")
@RestController
@RequestMapping("/manage/jianCeDevice")
@Validated
@RequiredArgsConstructor
public class JianCeDeviceController {
    private final JianCeDeviceApplicationService jianCeDeviceApplicationService;

    @Operation(summary = "添加监测设备")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddJianCeDeviceCommand command) {
        jianCeDeviceApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新监测设备")
    @PutMapping("/{jianCeDeviceId}")
    public ResponseDTO<Void> update(@PathVariable Long jianCeDeviceId, @RequestBody UpdateJianCeDeviceCommand command) {
        command.setJianCeDeviceId(jianCeDeviceId);
        jianCeDeviceApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除监测设备")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        jianCeDeviceApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取监测设备列表")
    @GetMapping
    public ResponseDTO<PageDTO<JianCeDeviceDTO>> list(JianCeDeviceQuery query) {
        PageDTO<JianCeDeviceDTO> list = jianCeDeviceApplicationService.getJianCeDevices(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取监测设备信息")
    @GetMapping("/{jianCeDeviceId}")
    public ResponseDTO<JianCeDeviceDTO> info(
            @PathVariable(value = "jianCeDeviceId", required = false) Long jianCeDeviceId) {
        JianCeDeviceDTO jianCeDeviceDTO = jianCeDeviceApplicationService.getJianCeDeviceInfo(jianCeDeviceId);
        return ResponseDTO.ok(jianCeDeviceDTO);
    }

    @Operation(summary = "生命体征数据列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, JianCeDeviceQuery query) {
        PageDTO<JianCeDeviceDTO> list = jianCeDeviceApplicationService.getJianCeDevices(query);
        CustomExcelUtil.writeToResponse(list.getRows(), JianCeDeviceDTO.class, response);
    }

    @GetMapping("/getOnlineCount")
    public ResponseDTO<Map<String, Object>> getOnlineCount() {
        return ResponseDTO.ok(jianCeDeviceApplicationService.getOnlineCount());
    }
}
