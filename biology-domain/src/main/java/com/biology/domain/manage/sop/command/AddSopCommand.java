package com.biology.domain.manage.sop.command;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddSopCommand {
    @Schema(description = "sop 编号")
    private String code;

    @Schema(description = "sop 名称")
    private String name;

    @Schema(description = "sop 范围")
    private String scope;

    @Schema(description = "sop 内容路径")
    private List<String> paths;

    // @Schema(description = "设备Ids")
    // private List<Long> equipmentIds;
}
