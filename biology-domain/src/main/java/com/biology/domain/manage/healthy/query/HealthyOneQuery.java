package com.biology.domain.manage.healthy.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HealthyOneQuery {
    @Schema(description = "人员id")
    private Long personnelId;

    @Schema(description = "类型")
    private String type;
}
