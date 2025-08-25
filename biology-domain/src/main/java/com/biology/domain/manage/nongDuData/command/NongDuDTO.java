package com.biology.domain.manage.nongDuData.command;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NongDuDTO {

    private String deviceSn;

    /** 粒子浓度 */
    @JsonAlias("ParticleConcentration")
    private Integer particleConcentration; // 示例值: 125

    /** 生物浓度 */
    @JsonAlias("BiologicalConcentration")
    private Integer biologicalConcentration; // 示例值: 320

    /** 设备工作状态 (0=停止, 1=启动) */
    @JsonAlias("WorkingStatus")
    private Integer workingStatus; // 示例值: 1

    /** 报警状态 (0=正常, 1=报警) */
    @JsonAlias("Alarm")
    private Integer alarm; // 示例值: 0

    public Double getParticleConcentrationToDouble() {
        return particleConcentration == null ? null : particleConcentration.doubleValue();
    }

    public Double getBiologicalConcentrationToDouble() {
        return biologicalConcentration == null ? null : biologicalConcentration.doubleValue();
    }
}
