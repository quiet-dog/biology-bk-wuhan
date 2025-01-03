package com.biology.domain.manage.emergencyEvent.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.x509.sigi.PersonalData;
import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmEntity;
import com.biology.domain.manage.emergencyAlarm.dto.EmergencyAlarmDTO;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventEntity;
import com.biology.domain.manage.emergencyEvent.db.EmergencyUserEntity;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmergencyEventDTO {
    @Schema(description = "id")
    @ExcelColumn(name = "事件编号")
    private Long emergencyEventId;

    @Schema(description = "事件名称")
    @ExcelColumn(name = "事件名称")
    private String eventName;

    @Schema(description = "事件编号")
    // @ExcelColumn(name = "事件编号")
    private String code;

    @Schema(description = "事件类型")
    @ExcelColumn(name = "事件类型")
    private String type;

    @Schema(description = "处理流程")
    @ExcelColumn(name = "处理流程")
    private String processingFlow;

    @Schema(description = "事件内容")
    @ExcelColumn(name = "事件内容")
    private String content;

    @Schema(description = "处理人")
    @ExcelColumn(name = "处理人")
    private String handlerNames;

    private Date createTime;

    private List<PersonnelDTO> handlers;

    private List<EmergencyAlarmDTO> emergencyAlarmDTOs;

    public EmergencyEventDTO(EmergencyEventEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addHandler();
            addAlarm();
        }
    }

    public void addHandler() {
        PersonnelEntity personnelEntityDB = new PersonnelEntity();
        QueryWrapper<PersonnelEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("personnel_id",
                "select personnel_id from manage_emergency_event_personnel where emergency_event_id = "
                        + getEmergencyEventId());
        List<PersonnelEntity> list = personnelEntityDB.selectList(queryWrapper);
        handlers = new ArrayList<>();
        for (PersonnelEntity personnelEntity : list) {
            PersonnelDTO personnelDTO = new PersonnelDTO(personnelEntity);
            handlers.add(personnelDTO);
        }
        handlerNames = "";
        for (PersonnelDTO personnelDTO : handlers) {
            handlerNames = handlerNames + personnelDTO.getName() + ",";
        }
    }

    public void addAlarm() {
        EmergencyAlarmEntity emergencyAlarmEntityDB = new EmergencyAlarmEntity();
        QueryWrapper<EmergencyAlarmEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("emergency_alarm_id",
                "select emergency_alarm_id from manage_emergency_event_alarm where emergency_event_id = "
                        + getEmergencyEventId());
        List<EmergencyAlarmEntity> list = emergencyAlarmEntityDB.selectList(queryWrapper);
        emergencyAlarmDTOs = new ArrayList<>();
        for (EmergencyAlarmEntity emergencyAlarmEntity : list) {
            EmergencyAlarmDTO emergencyAlarmDTO = new EmergencyAlarmDTO(emergencyAlarmEntity);
            emergencyAlarmDTOs.add(emergencyAlarmDTO);
        }
    }
}
