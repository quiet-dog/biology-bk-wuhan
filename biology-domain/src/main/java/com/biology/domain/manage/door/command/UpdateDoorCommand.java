package com.biology.domain.manage.door.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateDoorCommand extends AddDoorCommand {
    @Schema(description = "门禁Id")
    private Long doorId;
}
