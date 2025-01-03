package com.biology.domain.manage.craftarchive.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateCraftArchiveCommand extends AddCraftArchiveCommand {

    @Schema(description = "工艺档案ID")
    private Long craftArchiveId;
}