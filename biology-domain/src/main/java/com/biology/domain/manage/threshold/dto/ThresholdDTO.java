package com.biology.domain.manage.threshold.dto;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.db.EmergencyFileEntity;
import com.biology.domain.manage.emergency.dto.EmergencyDTO;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.sop.db.SopEntity;
import com.biology.domain.manage.sop.db.SopFileEntity;
import com.biology.domain.manage.sop.dto.SopDTO;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyEntity;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdSopEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ThresholdDTO {
    private Long thresholdId;

    @Schema(description = "设备Id")
    private Long equipmentId;

    @Schema(description = "传感器名称")
    private String code;
    // 区间
    @Schema(description = "区间")
    private String interval;
    // 级别
    @Schema(description = "级别")
    private String level;
    // 购置日期
    @Schema(description = "购置日期")
    private Date purchaseDate;
    // 使用状态
    @Schema(description = "使用状态")
    private String useStatus;

    private String outId;

    @Schema(description = "传感器型号")
    private String sensorModel;

    @Schema(description = "设备指标")
    private String equipmentIndex;

    @Schema(description = "传感器名称")
    private String sensorName;

    @Schema(description = "传感器名称")
    private String unit;

    private List<ThresholdValueEntity> values;

    private EquipmentDTO equipment;

    @Schema(description = "应急预案列表")
    private List<Long> emergencys;

    private List<String> emergencyPaths;

    @Schema(description = "SOP列表")
    private List<Long> sops;

    private List<String> sopPaths;

    @Schema(description = "创建时间")
    private Date createTime;

    public ThresholdDTO(ThresholdEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            if (getEquipmentId() != null) {
                EquipmentEntity equipmentEntity = new EquipmentEntity();
                equipmentEntity = equipmentEntity.selectById(getEquipmentId());
                equipment = new EquipmentDTO(equipmentEntity);
            }

            insertValues();
            insertEmergency();
            insertSop();
        }
    }

    public void insertValues() {
        // ThresholdValueEntity thresholdValueEntity = new ThresholdValueEntity();
        // QueryWrapper<ThresholdValueEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("threshold_id", getThresholdId());
        setValues(CacheCenter.thresholdValuesCache.getObjectById(getThresholdId()));
    }

    public void insertEmergency() {
        // if (emergencys == null) {
        // emergencys = new ArrayList<>();
        // }
        // ThresholdEmergencyEntity thresholdEmergencyEntityDB = new
        // ThresholdEmergencyEntity();
        // QueryWrapper<ThresholdEmergencyEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("threshold_id", getThresholdId());
        // List<ThresholdEmergencyEntity> thresholdEmergencyEntities =
        // thresholdEmergencyEntityDB.selectList(queryWrapper);
        // for (ThresholdEmergencyEntity thresholdEmergencyEntity :
        // thresholdEmergencyEntities) {
        // EmergencyEntity emergencyEntity = new EmergencyEntity();
        // emergencyEntity =
        // emergencyEntity.selectById(thresholdEmergencyEntity.getEmergencyId());
        // emergencys.add(new EmergencyDTO(emergencyEntity));
        // }
        // emergencys = CacheCenter.thrsholdEmergencyCache.getObjectById(thresholdId);
        QueryWrapper<ThresholdEmergencyEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("threshold_id", getThresholdId());
        List<ThresholdEmergencyEntity> thresholdEmergencyEntities = new ThresholdEmergencyEntity()
                .selectList(queryWrapper);
        if (thresholdEmergencyEntities != null && thresholdEmergencyEntities.size() > 0) {
            emergencys = new ArrayList<>();
            emergencyPaths = new ArrayList<>();
            for (ThresholdEmergencyEntity thresholdEmergencyEntity : thresholdEmergencyEntities) {
                emergencys.add(thresholdEmergencyEntity.getEmergencyId());
            }
            if (emergencys != null && emergencys.size() > 0) {
                new EmergencyFileEntity()
                        .selectList(new QueryWrapper<EmergencyFileEntity>().in("emergency_id", emergencys))
                        .forEach(emergencyFileEntity -> {
                            emergencyPaths.add(emergencyFileEntity.getPath());
                        });
            }
        }
    }

    public void insertSop() {
        // sops = CacheCenter.thrsholdSopCache.getObjectById(thresholdId);
        // if (sops == null) {
        // sops = new ArrayList<>();
        // }
        // ThresholdSopEntity thresholdSopEntityDB = new ThresholdSopEntity();
        // QueryWrapper<ThresholdSopEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("threshold_id", getThresholdId());
        // List<ThresholdSopEntity> thresholdSopEntities =
        // thresholdSopEntityDB.selectList(queryWrapper);
        // for (ThresholdSopEntity thresholdSopEntity : thresholdSopEntities) {
        // SopEntity sopEntity = new SopEntity();
        // sopEntity = sopEntity.selectById(thresholdSopEntity.getSopId());
        // sops.add(new SopDTO(sopEntity));
        // }
        QueryWrapper<ThresholdSopEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("threshold_id", getThresholdId());
        List<ThresholdSopEntity> thresholdSopEntities = new ThresholdSopEntity().selectList(queryWrapper);
        if (thresholdSopEntities != null && thresholdSopEntities.size() > 0) {
            sops = new ArrayList<>();
            sopPaths = new ArrayList<>();
            for (ThresholdSopEntity thresholdSopEntity : thresholdSopEntities) {
                sops.add(thresholdSopEntity.getSopId());
            }
            if (sops != null && sops.size() > 0) {
                new SopFileEntity().selectList(new QueryWrapper<SopFileEntity>().in("sop_id", sops))
                        .forEach(sopEntity -> {
                            sopPaths.add(sopEntity.getPath());
                        });
            }
        }
    }
}
