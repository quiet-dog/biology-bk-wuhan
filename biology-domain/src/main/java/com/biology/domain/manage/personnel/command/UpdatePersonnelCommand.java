package com.biology.domain.manage.personnel.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdatePersonnelCommand extends AddPersonnelCommand {
    @Schema(description = "员工ID")
    private Long personnelId;
}
