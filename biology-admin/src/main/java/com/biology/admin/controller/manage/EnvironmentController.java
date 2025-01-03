package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
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

import com.biology.admin.customize.aop.accessLog.AccessLog;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.enums.common.BusinessTypeEnum;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.environment.EnvironmentApplicationService;
import com.biology.domain.manage.environment.command.AddEnvironmentCommand;
import com.biology.domain.manage.environment.command.UpdateEnvironmentCommand;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;
import com.biology.domain.manage.environment.dto.EnvironmentEchartDTO;
import com.biology.domain.manage.environment.dto.EnvironmentStatisticsDTO;
import com.biology.domain.manage.environment.dto.EnvironmentTypesDTO;
import com.biology.domain.manage.environment.query.EnvironmentQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "环境档案API", description = "环境档案的增删查改")
@RestController
@RequestMapping("/manage/environment")
@Validated
@RequiredArgsConstructor
public class EnvironmentController extends BaseController {
    private final EnvironmentApplicationService environmentApplicationService;

    @Operation(summary = "添加环境档案")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEnvironmentCommand command) {
        environmentApplicationService.addEnvironment(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新环境档案")
    @PutMapping("/{environmentId}")
    public ResponseDTO<Void> update(@PathVariable Long environmentId, @RequestBody UpdateEnvironmentCommand command) {
        command.setEnvironmentId(environmentId);
        environmentApplicationService.updateEnvironment(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除环境档案")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> environmentIds) {
        environmentApplicationService.deleteEnvironments(environmentIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取环境档案详情")
    @GetMapping("/{environmentId}")
    public ResponseDTO<EnvironmentDTO> getEnvironmentInfo(
            @PathVariable(value = "environmentId", required = false) Long environmentId) {
        EnvironmentDTO environmentDetailDTO = environmentApplicationService.getEnvironment(environmentId);
        return ResponseDTO.ok(environmentDetailDTO);
    }

    @Operation(summary = "获取环境档案列表")
    @GetMapping
    public ResponseDTO<PageDTO<EnvironmentDTO>> list(EnvironmentQuery query) {
        PageDTO<EnvironmentDTO> list = environmentApplicationService.searchEnvironments(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "用户列表导出")
    @AccessLog(title = "用户管理", businessType = BusinessTypeEnum.EXPORT)
    @PreAuthorize("@permission.has('system:user:export')")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, EnvironmentQuery query) {
        PageDTO<EnvironmentDTO> userList = environmentApplicationService.searchEnvironments(query);
        CustomExcelUtil.writeToResponse(userList.getRows(), EnvironmentDTO.class, response);
    }

    @Operation(summary = "获取环境档案统计信息")
    @GetMapping("/data/{environmentId}")
    public ResponseDTO<List<EnvironmentEchartDTO>> getDayStatistics(
            @PathVariable(value = "environmentId", required = false) Long environmentId) {
        return ResponseDTO.ok(environmentApplicationService.getDayStatistics(environmentId));
    }

    @Operation(summary = "获取环境档案所有的分类")
    @GetMapping("/allGroup")
    public ResponseDTO<EnvironmentTypesDTO> getAllGroup() {
        return ResponseDTO.ok(environmentApplicationService.getAllGroup());
    }
}
