package com.biology.domain.manage.emergency.command;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "添加应急预案")
@Data
public class AddEmergencyCommand {

    @Schema(description = "应急编号")
    private String code;

    @Schema(description = "应急名称")
    private String title;

    @Schema(description = "应急版本号")
    private String version;

    @Schema(description = "颁发部门id")
    private Long deptId;

    @Schema(description = "适用范围")
    private String scope;

    @Schema(description = "风险类型")
    private String riskType;

    @Schema(description = "应急文件路径")
    private List<String> paths;

    @Schema(description = "关键词id")
    private List<Long> keywordIds;

    @Schema(description = "设备id")
    private List<Long> equipmentIds;
}
