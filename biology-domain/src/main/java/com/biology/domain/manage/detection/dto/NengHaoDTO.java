package com.biology.domain.manage.detection.dto;

import java.util.Date;

import com.biology.domain.manage.detection.db.DetectionEntity;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.biology.common.annotation.ExcelColumn;

@Data
public class NengHaoDTO {

    @Schema(description = "数据ID")
    private Long detectionId;

    @Schema(description = "环境ID")
    private Long environmentId;

    @Schema(description = "环境信息")
    private EnvironmentDTO environment;

    @Schema(description = "数值")
    @ExcelColumn(name = "当天能耗量")
    private double value;

    @Schema(description = "水用量")
    @ExcelColumn(name = "水用量")
    private double waterValue;

    @Schema(description = "用电量")
    @ExcelColumn(name = "用电量")
    private double electricityValue;

    @Schema(description = "创建时间")
    @ExcelColumn(name = "创建时间")
    private Date createTime;
    // 本月的全部使用量
    @ExcelColumn(name = "本月能耗量")
    private double totalValue;

    @ExcelColumn(name = "类型")
    private String type;

    private Double cureentvalue;

    @ExcelColumn(name = "描述")
    private String description;

    public NengHaoDTO(DetectionDTO detectionDTO) {
        if (detectionDTO != null) {
            this.detectionId = detectionDTO.getDetectionId();
            this.environmentId = detectionDTO.getEnvironmentId();
            this.environment = detectionDTO.getEnvironment();
            this.value = detectionDTO.getValue();
            this.waterValue = detectionDTO.getWaterValue();
            this.electricityValue = detectionDTO.getElectricityValue();
            this.createTime = detectionDTO.getCreateTime();

            if (detectionDTO.getWaterValue() != 0) {
                this.type = "水";
                this.value = this.waterValue;
            } else if (detectionDTO.getElectricityValue() != 0) {
                this.type = "电";
                this.value = this.electricityValue;
            } else {
                this.type = "未知";
            }
            if (getEnvironment() != null) {
                this.description = getEnvironment().getDescription();
            }
        }
    }

}
