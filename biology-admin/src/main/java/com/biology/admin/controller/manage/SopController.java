package com.biology.admin.controller.manage;

import java.util.List;

import javax.validation.constraints.NotNull;

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
import com.biology.domain.manage.policies.command.AddPoliciesCommand;
import com.biology.domain.manage.policies.command.UpdatePoliciesCommand;
import com.biology.domain.manage.policies.dto.PoliciesDTO;
import com.biology.domain.manage.policies.query.PoliciesQuery;
import com.biology.domain.manage.sop.SopApplicationService;
import com.biology.domain.manage.sop.command.AddSopCommand;
import com.biology.domain.manage.sop.command.UpdateSopCommand;
import com.biology.domain.manage.sop.dto.SopDTO;
import com.biology.domain.manage.sop.query.SearchSopQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "SOP", description = "SOP的增删查改")
@RestController
@RequestMapping("/manage/sop")
@Validated
@RequiredArgsConstructor
public class SopController extends BaseController {
    private final SopApplicationService sopApplicationService;

    @Operation(summary = "创建SOP")
    @PostMapping
    public ResponseDTO<Void> createPolicy(@RequestBody AddSopCommand command) {
        sopApplicationService.addSop(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取SOP信息")
    @GetMapping("{sopId}")
    public ResponseDTO<SopDTO> info(@PathVariable(value = "sopId", required = false) Long sopId) {
        SopDTO sopDTO = sopApplicationService.getSop(sopId);
        return ResponseDTO.ok(sopDTO);
    }

    @Operation(summary = "获取SOP列表")
    @GetMapping
    public ResponseDTO<PageDTO<SopDTO>> list(SearchSopQuery query) {
        PageDTO<SopDTO> list = sopApplicationService.getSopList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "跟新SOP")
    @PutMapping("{sopId}")
    public ResponseDTO<Void> edit(@PathVariable Long sopId, @RequestBody UpdateSopCommand command) {
        command.setSopId(sopId);
        sopApplicationService.updateSop(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除SOP")
    @DeleteMapping
    public ResponseDTO<Void> remove(@RequestParam @NotNull List<Long> sopIds) {
        sopApplicationService.deleteSops(sopIds);
        return ResponseDTO.ok();
    }
}
