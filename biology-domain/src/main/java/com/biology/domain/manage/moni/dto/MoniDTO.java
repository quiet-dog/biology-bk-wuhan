package com.biology.domain.manage.moni.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.moni.db.MoniEntity;
import com.biology.domain.manage.moni.db.MoniThresholdEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MoniDTO {

    @Schema(description = "模拟ID")
    private Long moniId;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "推送类型")
    private String pushType;

    @Schema(description = "最小值")
    private Double min;

    @Schema(description = "最大值")
    private Double max;

    @Schema(description = "推送频率")
    private Integer pushFrequency;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "阈值IDs")
    private List<Long> thresholdIds;

    @Schema(description = "环境IDs")
    private List<Long> environmentIds;

    public MoniDTO(MoniEntity moniEntity) {
        this.moniId = moniEntity.getMoniId();
        this.description = moniEntity.getDescription();
        this.pushType = moniEntity.getPushType();
        this.createTime = moniEntity.getCreateTime();
        this.pushFrequency = moniEntity.getPushFrequency();
        this.min = moniEntity.getMin();
        this.max = moniEntity.getMax();
        // setThresholdIdsQ();
        // setEquipmentIdsQ();
    }

    public Void setThresholdIdsQ() {
        this.setThresholdIds(new ArrayList<>());
        if (this.getMoniId() != null && this.getPushType() != null && this.getPushType().equals("1")) {
            QueryWrapper<MoniThresholdEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("moni_id", this.getMoniId());
            List<MoniThresholdEntity> moniThresholdEntities = new MoniThresholdEntity().selectList(queryWrapper);
            for (MoniThresholdEntity moniThresholdEntity : moniThresholdEntities) {
                this.thresholdIds.add(moniThresholdEntity.getThresholdId());
            }
        }
        return null;
    }

    public void setEnvironmentIdsQ() {
        this.setEnvironmentIds(new ArrayList<>());
        if (this.getMoniId() != null && this.getPushType() != null && this.getPushType().equals("1")) {
            QueryWrapper<MoniThresholdEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("moni_id", this.getMoniId());
            List<MoniThresholdEntity> moniThresholdEntities = new MoniThresholdEntity().selectList(queryWrapper);
            for (MoniThresholdEntity moniThresholdEntity : moniThresholdEntities) {
                this.environmentIds.add(moniThresholdEntity.getEnvironmentId());
            }
        }
    }
}
