package com.biology.domain.manage.detection.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.biology.domain.manage.detection.db.DetectionEntity;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DetectionDTO {
    @Schema(description = "数据ID")
    private Long detectionId;

    @Schema(description = "环境ID")
    private Long environmentId;

    @Schema(description = "环境信息")
    private EnvironmentDTO environment;

    @Schema(description = "数值")
    private double value;

    @Schema(description = "水用量")
    private double waterValue;

    @Schema(description = "用电量")
    private double electricityValue;

    @Schema(description = "创建时间")
    private Date createTime;

    public DetectionDTO(DetectionEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);

            if (getEnvironmentId() != null) {
                EnvironmentEntity environmentEntityDB = new EnvironmentEntity();
                EnvironmentEntity environmentEntity = environmentEntityDB.selectById(getEnvironmentId());
                if (environmentEntity != null) {
                    this.environment = new EnvironmentDTO(environmentEntity);
                }
                if (getValue() == 0) {
                    if (getElectricityValue() != 0) {
                        this.value = getElectricityValue();
                    } else if (getWaterValue() != 0) {
                        this.value = getWaterValue();
                    } else {
                        this.value = 0;
                    }
                }
            }
        }
    }
}
