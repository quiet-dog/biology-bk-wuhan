package com.biology.domain.manage.alarmlevel.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateAlarmlevelCommand extends AddAlarmlevelCommand {

    @Schema(description = "报警级别Id")
    private Long alarmlevelId;

}
