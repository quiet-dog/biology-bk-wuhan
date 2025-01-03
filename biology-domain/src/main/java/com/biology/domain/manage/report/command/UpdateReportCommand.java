package com.biology.domain.manage.report.command;

import lombok.Data;

@Data
public class UpdateReportCommand extends AddReportCommand {
    private Long reportId;
}
