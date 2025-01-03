package com.biology.domain.manage.knowledge.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateKnowledgeCommand extends AddKnowledgeCommand {
    @Schema(description = "知识库ID")
    private Long knowledgeId;

}
