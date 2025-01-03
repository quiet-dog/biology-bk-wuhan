package com.biology.domain.manage.receive.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddReceiveCommand {

    @Schema(description = "领用用户ID")
    private Long receiveUserId;

    @Schema(description = "物资ID")
    private Long materialsId;

    @Schema(description = "领用数量")
    private double receiveNum;

    @Schema(description = "领用说明")
    private String receiveExplain;

    @Schema(description = "外部ID")
    private Long outId;

}
