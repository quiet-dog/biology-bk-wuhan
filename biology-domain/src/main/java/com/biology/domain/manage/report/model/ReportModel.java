package com.biology.domain.manage.report.model;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.alarm.command.AddAlarmCommand;
import com.biology.domain.manage.alarm.db.AlarmEntity;
import com.biology.domain.manage.alarm.db.AlarmService;
import com.biology.domain.manage.alarm.model.AlarmFactory;
import com.biology.domain.manage.alarm.model.AlarmModel;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.db.MaterialsValueEntity;
import com.biology.domain.manage.materials.model.MaterialsFactory;
import com.biology.domain.manage.materials.model.MaterialsModel;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.report.command.AddReportCommand;
import com.biology.domain.manage.report.command.UpdateReportCommand;
import com.biology.domain.manage.report.db.ReportEntity;
import com.biology.domain.manage.report.db.ReportFileEntity;
import com.biology.domain.manage.report.db.ReportFileService;
import com.biology.domain.manage.report.db.ReportService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ReportModel extends ReportEntity {

    private ReportService reportService;

    private MaterialsService materialsService;

    private PersonnelService personnelService;

    private MaterialsFactory materialsFactory;

    private AlarmService alarmService;

    private AlarmFactory alarmFactory;

    private ReportFileService reportFileService;

    private List<String> paths;

    public ReportModel(ReportService reportService, MaterialsService materialsService,
            PersonnelService personnelService, MaterialsFactory materialsFactory, AlarmService alarmService,
            ReportFileService reportFileService, AlarmFactory alarmFactory) {
        this.reportService = reportService;
        this.materialsService = materialsService;
        this.personnelService = personnelService;
        this.materialsFactory = materialsFactory;
        this.alarmService = alarmService;
        this.reportFileService = reportFileService;
        this.alarmFactory = alarmFactory;
    }

    public ReportModel(ReportEntity entity, ReportService reportService, MaterialsService materialsService,
            PersonnelService personnelService, MaterialsFactory materialsFactory, AlarmService alarmService,
            ReportFileService reportFileService, AlarmFactory alarmFactory) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
        }
        this.reportService = reportService;
        this.materialsService = materialsService;
        this.personnelService = personnelService;
        this.materialsFactory = materialsFactory;
        this.alarmService = alarmService;
        this.reportFileService = reportFileService;
        this.alarmFactory = alarmFactory;
    }

    public void loadAddReportCommand(AddReportCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "reportId");
        }
    }

    public void loadUpdateReportCommand(UpdateReportCommand command) {
        if (command != null) {
            loadAddReportCommand(command);
        }
    }

    public void checkAlarm() {
        AlarmModel alarmModel = alarmFactory.create();
        AddAlarmCommand addAlarmCommand = new AddAlarmCommand();
        addAlarmCommand.setMaterialsId(getMaterialsId());
        addAlarmCommand.setNum(getReportNum());
        alarmModel.loadAddCommand(addAlarmCommand);
        Long alarmId = alarmModel.insertToAlarmId();
        UpdateWrapper<AlarmEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("alarm_id", alarmId).set("report_id", getReportId());
        // AlarmEntity alarm = new AlarmEntity();
        // alarm.setReportId(getReportId());
        // alarmService.update(alarm, updateWrapper);
        this.alarmService.getBaseMapper().update(null, updateWrapper);

    }

    public void addFiles() {
        if (paths != null && !paths.isEmpty()) {
            paths.forEach(path -> {
                ReportFileEntity reportFileEntity = new ReportFileEntity();
                reportFileEntity.setPath(path);
                reportFileEntity.setReportId(getReportId());
                reportFileService.save(reportFileEntity);
            });
        }
    }

    public void cleanFiles() {
        QueryWrapper<ReportFileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("report_id", getReportId());
        reportFileService.remove(queryWrapper);
    }

    public boolean insert() {
        MaterialsEntity materialsEntity = materialsService.getById(getMaterialsId());
        if (materialsEntity.getStock() - getReportNum() < 0) {
            this.setReportNum(0);
        } else {
            materialsEntity.setStock(materialsEntity.getStock() - getReportNum());
        }
        this.setBatch(materialsEntity.getBatch());
        super.insert();
        checkAlarm();
        addFiles();
        return true;
    }
}
