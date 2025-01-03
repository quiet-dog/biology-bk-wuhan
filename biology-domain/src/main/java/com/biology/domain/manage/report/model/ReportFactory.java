package com.biology.domain.manage.report.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.alarm.db.AlarmService;
import com.biology.domain.manage.alarm.model.AlarmFactory;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.model.MaterialsFactory;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.report.db.ReportEntity;
import com.biology.domain.manage.report.db.ReportFileService;
import com.biology.domain.manage.report.db.ReportService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportFactory {

    private final ReportService reportService;

    private final MaterialsService materialsService;

    private final PersonnelService personnelService;

    private final MaterialsFactory materialsFactory;

    private final AlarmService alarmService;

    private final ReportFileService reportFileService;

    private final AlarmFactory alarmFactory;

    public ReportModel create() {
        return new ReportModel(reportService, materialsService, personnelService, materialsFactory,
                alarmService, reportFileService, alarmFactory);
    }

    public ReportModel loadById(Long reportId) {
        ReportEntity byId = reportService.getById(reportId);
        if (byId == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, reportId, "报告");
        }
        return new ReportModel(byId, reportService, materialsService, personnelService, materialsFactory, alarmService,
                reportFileService, alarmFactory);
    }
}
