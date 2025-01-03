package com.biology.domain.manage.knowledge.command;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddKnowledgeCommand {

    @Schema(description = "知识库编号")
    private String code;

    @Schema(description = "知识库标题")
    private String title;

    @Schema(description = "知识库类型")
    private String knowledgeType;

    @Schema(description = "知识库内容路径")
    private List<String> paths;
}
