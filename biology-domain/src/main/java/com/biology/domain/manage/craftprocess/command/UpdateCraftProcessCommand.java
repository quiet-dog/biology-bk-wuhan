package com.biology.domain.manage.craftprocess.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateCraftProcessCommand extends AddCraftProcessCommand {

    @Schema(description = "工艺流程图ID")
    private Long craftProcessId;
}
