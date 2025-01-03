package com.biology.domain.manage.emergencyAlarm.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.detection.db.DetectionEntity;
import com.biology.domain.manage.emergency.db.EmergencyFileEntity;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyAlarmEntity;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.sop.db.SopFileEntity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmergencyAlarmDTO {

    @ApiModelProperty("应急ID")
    private Long emergencyAlarmId;

    @Schema(description = "设备数据id")
    private Long equipmentDataId;

    @Schema(description = "环境数据Id")
    private Long detectionId;

    @ApiModelProperty("应急编号")
    @Schema(description = "应急编号")
    private String code;

    @Schema(description = "报警级别")
    private String level;

    @ApiModelProperty("报警类型")
    @Schema(description = "报警类型")
    private String type;

    @ApiModelProperty("报警描述")
    @Schema(description = "报警描述")
    private String description;

    @Schema(description = "报警时间")
    private Date createTime;

    private List<String> paths;

    public EmergencyAlarmDTO(EmergencyAlarmEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addPaths();
        }
    }

    public void addPaths() {
        if (getEmergencyAlarmId() != null) {
            paths = new ArrayList<>();
            QueryWrapper<SopFileEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.inSql("sop_id",
                    "select sop_id from manage_emergency_connect where emergency_alarm_id = " + getEmergencyAlarmId());
            List<SopFileEntity> list = new SopFileEntity().selectList(queryWrapper);
            if (list != null && list.size() > 0) {
                for (SopFileEntity entity : list) {
                    paths.add(entity.getPath());
                }
            }

            QueryWrapper<EmergencyFileEntity> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.inSql("emergency_id",
                    "select emergency_id from manage_emergency_connect where emergency_alarm_id = "
                            + getEmergencyAlarmId());
            List<EmergencyFileEntity> list2 = new EmergencyFileEntity().selectList(queryWrapper2);
            if (list2 != null && list2.size() > 0) {
                for (EmergencyFileEntity entity : list2) {
                    paths.add(entity.getPath());
                }
            }
        }
    }

}
