package com.biology.admin.controller.manage;

import java.util.List;

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
import com.biology.domain.manage.alarmlevel.AlarmlevelApplicationService;
import com.biology.domain.manage.alarmlevel.command.AddAlarmlevelCommand;
import com.biology.domain.manage.alarmlevel.command.UpdateAlarmlevelCommand;
import com.biology.domain.manage.alarmlevel.dto.AlarmlevelDTO;
import com.biology.domain.manage.alarmlevel.query.AlarmlevelQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "报警级别API", description = "报警级别的增删查改")
@RestController
@RequestMapping("/manage/alarmlevel")
@Validated
@RequiredArgsConstructor
public class AlarmlevelController extends BaseController {
    private final AlarmlevelApplicationService alarmlevelApplicationService;

    @Operation(summary = "添加环境报警级别")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddAlarmlevelCommand command) {
        alarmlevelApplicationService.addAlarmlevel(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新环境报警级别")
    @PutMapping("/{alarmlevelId}")
    public ResponseDTO<Void> update(@PathVariable Long alarmlevelId, @RequestBody UpdateAlarmlevelCommand command) {
        command.setAlarmlevelId(alarmlevelId);
        alarmlevelApplicationService.updateAlarmlevel(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除环境报警级别")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> alarmlevelIds) {
        alarmlevelApplicationService.deleteAlarmlevels(alarmlevelIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取环境报警级别详情")
    @GetMapping("/{alarmlevelId}")
    public ResponseDTO<AlarmlevelDTO> getAlarmlevelInfo(
            @PathVariable(value = "alarmlevelId", required = false) Long alarmlevelId) {
        AlarmlevelDTO alarmlevelDetailDTO = alarmlevelApplicationService.getAlarmlevel(alarmlevelId);
        return ResponseDTO.ok(alarmlevelDetailDTO);
    }

    @Operation(summary = "获取环境报警级别列表")
    @GetMapping
    public ResponseDTO<PageDTO<AlarmlevelDTO>> list(AlarmlevelQuery query) {
        PageDTO<AlarmlevelDTO> list = alarmlevelApplicationService.searchAlarmlevels(query);
        return ResponseDTO.ok(list);
    }
}
