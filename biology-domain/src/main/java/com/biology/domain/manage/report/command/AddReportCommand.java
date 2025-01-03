package com.biology.domain.manage.report.command;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddReportCommand {

    @Schema(description = "报告编号")
    private String code;

    @Schema(description = "物资ID")
    private Long materialsId;

    @Schema(description = "物资Code")
    private String materialsCode;

    @Schema(description = "报告类型")
    private String reportType;

    @Schema(description = "报告数量")
    private double reportNum;

    @Schema(description = "上报原因")
    private String reportReason;

    @Schema(description = "上报文件列表")
    private List<String> paths;
}
