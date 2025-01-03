package com.biology.domain.manage.craftdisposemanual.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UpdateDisposeManualCommand", description = "更新处置手册命令")
public class UpdateDisposeManualCommand extends AddDisposeManualCommand {

    @ApiModelProperty("处置手册ID")
    private Long craftDisposeManualId;
}
