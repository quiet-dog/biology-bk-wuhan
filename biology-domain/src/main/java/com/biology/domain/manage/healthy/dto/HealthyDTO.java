package com.biology.domain.manage.healthy.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.healthy.db.HealthyEntity;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HealthyDTO {
    @Schema(defaultValue = "人员Id")
    private Long personnelId;

    private PersonnelDTO personnel;

    @Schema(defaultValue = "体温")
    private double temperature;

    @Schema(defaultValue = "心率")
    private double heartRate;

    @Schema(defaultValue = "收缩压强")
    private double highBloodPressure;

    @Schema(defaultValue = "舒张压")
    private double lowBloodPressure;

    @Schema(defaultValue = "创建时间")
    private Date createTime;

    private Long healthyId;

    public HealthyDTO(HealthyEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addPersonnelDTO();
        }
    }

    public void addPersonnelDTO() {
        if (getPersonnelId() != null) {
            this.personnel = new PersonnelDTO(new PersonnelEntity().selectById(getPersonnelId()));
        }
    }
}
