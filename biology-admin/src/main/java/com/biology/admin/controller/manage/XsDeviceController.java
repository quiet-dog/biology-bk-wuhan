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
import com.biology.domain.manage.xsDevice.XsDeviceApplicationService;
import com.biology.domain.manage.xsDevice.command.AddXsDeviceCommand;
import com.biology.domain.manage.xsDevice.command.UpdateXsDeviceCommand;
import com.biology.domain.manage.xsDevice.dto.XsDeviceDTO;
import com.biology.domain.manage.xsDevice.query.XsDeviceQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "消杀设备", description = "消杀设备的增删查改")
@RestController
@RequestMapping("/manage/xsDevice")
@Validated
@RequiredArgsConstructor
public class XsDeviceController extends BaseController {
    private final XsDeviceApplicationService xsDeviceApplicationService;

    @Operation(summary = "添加消杀设备")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddXsDeviceCommand command) {
        xsDeviceApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新消杀设备")
    @PutMapping("/{xsDeviceId}")
    public ResponseDTO<Void> update(@PathVariable Long xsDeviceId, @RequestBody UpdateXsDeviceCommand command) {
        command.setXsDeviceId(xsDeviceId);
        xsDeviceApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除消杀设备")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        xsDeviceApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取消杀设备列表")
    @GetMapping
    public ResponseDTO<PageDTO<XsDeviceDTO>> list(XsDeviceQuery query) {
        PageDTO<XsDeviceDTO> list = xsDeviceApplicationService.getXsDevices(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取消杀设备信息")
    @GetMapping("/{xsDeviceId}")
    public ResponseDTO<XsDeviceDTO> info(@PathVariable(value = "xsDeviceId", required = false) Long xsDeviceId) {
        XsDeviceDTO xsDeviceDTO = xsDeviceApplicationService.getXsDeviceInfo(xsDeviceId);
        return ResponseDTO.ok(xsDeviceDTO);
    }

    @Operation(summary = "行为监测数据列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, XsDeviceQuery query) {
        PageDTO<XsDeviceDTO> list = xsDeviceApplicationService.getXsDevices(query);
        CustomExcelUtil.writeToResponse(list.getRows(), XsDeviceDTO.class, response);
    }

    @GetMapping("/getOnlineCount")
    public ResponseDTO<Map<String, Object>> getOnlineCount() {
        return ResponseDTO.ok(xsDeviceApplicationService.getOnlineCount());
    }
}
