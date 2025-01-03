package com.biology.admin.controller.manage;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.knowledge.KnowledgeApplicationService;
import com.biology.domain.manage.knowledge.command.AddKnowledgeCommand;
import com.biology.domain.manage.knowledge.command.AddKnowledgeTypeCommand;
import com.biology.domain.manage.knowledge.command.UpdateKnowledgeCommand;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeService;
import com.biology.domain.manage.knowledge.dto.KnowledgeDTO;
import com.biology.domain.manage.knowledge.query.SearchKnowledgeQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "知识库API", description = "知识库的增删查改")
@RestController
@RequestMapping("/manage/knowledge")
@Validated
@RequiredArgsConstructor
public class KnowledgeController extends BaseController {

    private final KnowledgeApplicationService knowledgeApplicationService;

    private final KnowledgeTypeService knowledgeTypeService;

    @Operation(summary = "添加知识库")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddKnowledgeCommand command) {
        knowledgeApplicationService.addKnowledge(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新添加知识库")
    @PutMapping("/{knowledgeId}")
    public ResponseDTO<Void> update(@PathVariable("knowledgeId") Long knowledgeId,
            @Validated @RequestBody UpdateKnowledgeCommand command) {
        command.setKnowledgeId(knowledgeId);
        knowledgeApplicationService.updateKnowledge(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除添加知识库")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> knowledgeIds) {
        knowledgeApplicationService.deleteKnowledges(knowledgeIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取添加知识库列表")
    @GetMapping
    public ResponseDTO<PageDTO<KnowledgeDTO>> list(SearchKnowledgeQuery query) {
        PageDTO<KnowledgeDTO> list = knowledgeApplicationService.getKnowledgeList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取知识库信息")
    @GetMapping("/{knowledgeId}")
    public ResponseDTO<KnowledgeDTO> info(
            @PathVariable(value = "knowledgeId", required = false) Long knowledgeId) {
        KnowledgeDTO knowledgeDTO = knowledgeApplicationService.getKnowledgeInfo(knowledgeId);
        return ResponseDTO.ok(knowledgeDTO);
    }

    @Operation(summary = "添加知识库类型")
    @PostMapping("/type")
    public ResponseDTO<Void> addType(@RequestBody AddKnowledgeTypeCommand command) {
        knowledgeApplicationService.addKnowledgeType(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取知识库类型列表")
    @GetMapping("/type")
    public ResponseDTO<List<KnowledgeTypeEntity>> getTypeList() {
        List<KnowledgeTypeEntity> list = knowledgeTypeService.list();
        return ResponseDTO.ok(list);
    }

}
