package com.biology.domain.manage.craftnode.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateCraftNodeCommand extends AddCraftNodeCommand {

    @Schema(description = "工艺节点ID")
    private Long nodeId;
}
