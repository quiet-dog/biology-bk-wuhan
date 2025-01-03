package com.biology.admin.controller.manage;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.policies.PoliciesApplicationService;
import com.biology.domain.manage.policies.command.AddPoliciesCommand;
import com.biology.domain.manage.policies.command.UpdatePoliciesCommand;
import com.biology.domain.manage.policies.dto.PoliciesDTO;
import com.biology.domain.manage.policies.query.PoliciesQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "政策法规", description = "政策法规的增删查改")
@RestController
@RequestMapping("/manage/policies")
@Validated
@RequiredArgsConstructor// 添加跨域注解
public class PoliciesController extends BaseController {

    private final PoliciesApplicationService policiesApplicationService;

    @Operation(summary = "创建政策法规")
    @PostMapping
    public ResponseDTO<Void> createPolicy(@RequestBody AddPoliciesCommand command) {
        policiesApplicationService.createPolicy(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取政策法规信息")
    @GetMapping("{policiesId}")
    public ResponseDTO<PoliciesDTO> info(@PathVariable(value = "policiesId", required = false) Long policiesId) {
        PoliciesDTO policiesDTO = policiesApplicationService.getPoliciesInfo(policiesId);
        return ResponseDTO.ok(policiesDTO);
    }

    @Operation(summary = "获取政策法规列表")
    @GetMapping
    public ResponseDTO<PageDTO<PoliciesDTO>> list(PoliciesQuery query) {
        PageDTO<PoliciesDTO> list = policiesApplicationService.getPoliciesList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "跟新政策法规")
    @PutMapping("{policiesId}")
    public ResponseDTO<Void> edit(@RequestBody UpdatePoliciesCommand command) {
        policiesApplicationService.updatePolicies(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除政策法规")
    @DeleteMapping
    public ResponseDTO<Void> remove(@RequestParam @NotNull List<Long> policiesIds) {
        policiesApplicationService.deletes(policiesIds);
        return ResponseDTO.ok();
    }

}
