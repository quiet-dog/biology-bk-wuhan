package com.biology.domain.manage.environment.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.manage.alarmlevel.command.AlarmDetail;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelEntity;
import com.biology.domain.manage.alarmlevel.dto.AlarmlevelDTO;
import com.biology.domain.manage.alarmlevel.dto.AlarmlevelDetailDTO;
import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.dto.EmergencyDTO;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.sop.db.SopEntity;
import com.biology.domain.manage.sop.dto.SopDTO;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnvironmentDTO {
    @Schema(description = "环境ID")
    @ExcelColumn(name = "序号")
    private Long environmentId;

    // 环境描述
    @Schema(description = "环境描述")
    @ExcelColumn(name = "环境描述")
    private String description;

    // 位号
    @Schema(description = "位号")
    @ExcelColumn(name = "位号")
    private String tag;

    @Schema(description = "区域")
    @ExcelColumn(name = "区域")
    private String eArea;

    // 类型
    @Schema(description = "类型")
    @ExcelColumn(name = "类型")
    private String type;

    // 范围
    @Schema(description = "范围")
    @ExcelColumn(name = "范围")
    private String scope;

    // @TableField(value = "monitoring_point")
    @Schema(description = "监测点位")
    @ExcelColumn(name = "监测点位")
    private String monitoringPoint;

    // 信号
    @Schema(description = "信号")
    @ExcelColumn(name = "信号")
    private String eSignal;

    // 设备仪表供应商
    @Schema(description = "设备仪表供应商")
    @ExcelColumn(name = "设备仪表供应商")
    private String supplier;

    // 设备仪表型号
    @Schema(description = "设备仪表型号")
    @ExcelColumn(name = "设备仪表型号")
    private String model;

    // 数值
    @Schema(description = "数值")
    @ExcelColumn(name = "数值")
    private double value;

    @Schema(description = "单位")
    @ExcelColumn(name = "单位")
    private String unit;

    @Schema(description = "单位名称")
    @ExcelColumn(name = "单位名称")
    private String unitName;

    @Schema(description = "创建时间")
    @ExcelColumn(name = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    @ExcelColumn(name = "更新时间")
    private Date updateTime;

    @Schema(description = "PLC地址")
    @ExcelColumn(name = "plc地址")
    private String plcAddress;

    @Schema(description = "应急预案信息")
    private List<EmergencyDTO> emergencies;

    @Schema(description = "sop信息")
    private List<SopDTO> sops;

    private List<Long> emergencyIds;

    private List<Long> sopIds;

    // @Schema(description = "报警级别")
    // private List<AlarmlevelDTO> alarmlevels;

    @Schema(description = "报警级别")
    private List<AlarmlevelDetailDTO> alarmlevels;

    public EnvironmentDTO(EnvironmentEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addAlarmLevels();
            addEmergencies();
            addSops();
        }
    }

    public void addAlarmLevels() {
        List<AlarmlevelDetailDTO> alarmlevelsDB = new ArrayList<>();
        AlarmlevelDetailEntity alarmlevelDetailEntity = new AlarmlevelDetailEntity();
        QueryWrapper<AlarmlevelDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("environment_id", getEnvironmentId());
        List<AlarmlevelDetailEntity> alarmlevelDetailList = alarmlevelDetailEntity.selectList(queryWrapper);
        for (AlarmlevelDetailEntity alarmlevelDetail : alarmlevelDetailList) {
            alarmlevelsDB.add(new AlarmlevelDetailDTO(alarmlevelDetail));
        }
        setAlarmlevels(alarmlevelsDB);
    }

    public void addEmergencies() {
        List<EmergencyDTO> emergenciesDB = new ArrayList<>();
        QueryWrapper<EmergencyEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("emergency_id",
                "select emergency_id from manage_environment_emergency where environment_id = " + getEnvironmentId());
        List<EmergencyEntity> list = new EmergencyEntity().selectList(queryWrapper);
        setEmergencyIds(new ArrayList<>());
        for (EmergencyEntity emergencyEntity : list) {
            emergenciesDB.add(new EmergencyDTO(emergencyEntity));
            getEmergencyIds().add(emergencyEntity.getEmergencyId());
        }
        setEmergencies(emergenciesDB);
    }

    public void addSops() {
        List<SopDTO> sopsDB = new ArrayList<>();
        QueryWrapper<SopEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("sop_id",
                "select sop_id from manage_environment_sop where environment_id = " + getEnvironmentId());
        List<SopEntity> list = new SopEntity().selectList(queryWrapper);
        setSopIds(new ArrayList<>());
        for (SopEntity sopEntity : list) {
            sopsDB.add(new SopDTO(sopEntity));
            getSopIds().add(sopEntity.getSopId());
        }
        setSops(sopsDB);
    }
}
