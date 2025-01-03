package com.biology.domain.manage.receive.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateReceiveCommand extends AddReceiveCommand {
    @Schema(description = "领用记录ID")
    private Long receiveId;
}
