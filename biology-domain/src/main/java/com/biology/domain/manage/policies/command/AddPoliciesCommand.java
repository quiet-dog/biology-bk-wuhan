package com.biology.domain.manage.policies.command;

import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddPoliciesCommand {

    @Schema(description = "政策法规编号")
    private String policiesCode;

    @Schema(description = "政策法规名称")
    private String policiesName;

    // @Schema(description = "发布日期")
    // private Date releaseDate;

    @Schema(description = "附件路径")
    private List<String> paths;

    @Schema(description = "类型")
    private String type;

}
