package com.biology.domain.manage.environment.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateEnvironmentCommand extends AddEnvironmentCommand {
    @Schema(description = "环境档案Id")
    private Long environmentId;
}
