package com.biology.domain.manage.threshold.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.emergency.db.EmergencyFileEntity;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDetailDTO;
import com.biology.domain.manage.sop.db.SopFileEntity;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyEntity;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdSopEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.websocket.dto.OnlineDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ThresholdDataDTO {
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

    @Schema(description = "应急预案列表")
    private List<Long> emergencys;

    private List<String> emergencyPaths;

    @Schema(description = "SOP列表")
    private List<Long> sops;

    private List<String> sopPaths;

    @Schema(description = "值")
    @ExcelColumn(name = "值")
    private Object value;

    private String valueStyle;

    @Schema(description = "创建时间")
    private Date createTime;

    public ThresholdDataDTO(ThresholdEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            // setValues(CacheCenter.thresholdValuesCache.getObjectById(getThresholdId()));
            insertValue();
        }
    }

    public void insertValues() {
        // ThresholdValueEntity thresholdValueEntity = new ThresholdValueEntity();
        // QueryWrapper<ThresholdValueEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("threshold_id", getThresholdId());
        setValues(CacheCenter.thresholdValuesCache.getObjectById(getThresholdId()));
    }

    public void insertEmergency() {

        // QueryWrapper<ThresholdEmergencyEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("threshold_id", getThresholdId());
        // List<ThresholdEmergencyEntity> thresholdEmergencyEntities = new
        // ThresholdEmergencyEntity()
        // .selectList(queryWrapper);
        // if (thresholdEmergencyEntities != null && thresholdEmergencyEntities.size() >
        // 0) {
        // emergencys = new ArrayList<>();
        // emergencyPaths = new ArrayList<>();
        // for (ThresholdEmergencyEntity thresholdEmergencyEntity :
        // thresholdEmergencyEntities) {
        // emergencys.add(thresholdEmergencyEntity.getEmergencyId());
        // }
        // if (emergencys != null && emergencys.size() > 0) {
        // new EmergencyFileEntity()
        // .selectList(new QueryWrapper<EmergencyFileEntity>().in("emergency_id",
        // emergencys))
        // .forEach(emergencyFileEntity -> {
        // emergencyPaths.add(emergencyFileEntity.getPath());
        // });
        // }
        // }

        emergencys = new ArrayList<>();
        emergencyPaths = new ArrayList<>();
        List<ThresholdEmergencyEntity> thresholdEmergencyEntities = CacheCenter.thresholdEmergencyCache
                .getObjectById(getThresholdId());
        if (thresholdEmergencyEntities != null && thresholdEmergencyEntities.size() > 0) {
            for (ThresholdEmergencyEntity thresholdEmergencyEntity : thresholdEmergencyEntities) {
                emergencys.add(thresholdEmergencyEntity.getEmergencyId());
                List<EmergencyFileEntity> emergencyFileEntities = CacheCenter.emergencyFileCache
                        .getObjectById(thresholdEmergencyEntity.getEmergencyId());
                if (emergencyFileEntities != null && emergencyFileEntities.size() > 0) {
                    for (EmergencyFileEntity emergencyFileEntity : emergencyFileEntities) {
                        emergencyPaths.add(emergencyFileEntity.getPath());
                    }
                }
            }
        }
    }

    public void insertSop() {
        // QueryWrapper<ThresholdSopEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("threshold_id", getThresholdId());
        // List<ThresholdSopEntity> thresholdSopEntities = new
        // ThresholdSopEntity().selectList(queryWrapper);
        // if (thresholdSopEntities != null && thresholdSopEntities.size() > 0) {
        // sops = new ArrayList<>();
        // sopPaths = new ArrayList<>();
        // for (ThresholdSopEntity thresholdSopEntity : thresholdSopEntities) {
        // sops.add(thresholdSopEntity.getSopId());
        // }
        // if (sops != null && sops.size() > 0) {
        // new SopFileEntity().selectList(new QueryWrapper<SopFileEntity>().in("sop_id",
        // sops))
        // .forEach(sopEntity -> {
        // sopPaths.add(sopEntity.getPath());
        // });
        // }
        // }

        sops = new ArrayList<>();
        sopPaths = new ArrayList<>();
        List<ThresholdSopEntity> thresholdSopEntities = CacheCenter.thresholdSopCache.getObjectById(getThresholdId());
        if (thresholdSopEntities != null && thresholdSopEntities.size() > 0) {
            for (ThresholdSopEntity thresholdSopEntity : thresholdSopEntities) {
                sops.add(thresholdSopEntity.getSopId());
                List<SopFileEntity> sopFileEntities = CacheCenter.sopFileCache
                        .getObjectById(thresholdSopEntity.getSopId());
                if (sopFileEntities != null && sopFileEntities.size() > 0) {
                    for (SopFileEntity sopFileEntity : sopFileEntities) {
                        sopPaths.add(sopFileEntity.getPath());
                    }
                }
            }
        }

    }

    public void insertValue() {
        if (getThresholdId() != null && getThresholdId() > 0) {
            String redisId = "threshold-" + getThresholdId().toString();
            OnlineDTO onlineDTO = CacheCenter.onlineCache.getObjectById(redisId);
            if (onlineDTO != null) {
                setValue(onlineDTO.getThresholdData());
            } else {
                setValue("暂无数据");
            }
        }
    }

    public void insertValueStyle() {
        if (getValue() != null && getValue() instanceof Double) {

            Double dV = Double.parseDouble(getValue().toString());

            List<ThresholdValueEntity> thresholdValueEntities = CacheCenter.thresholdValuesCache
                    .getObjectById(getThresholdId());
            if (thresholdValueEntities != null && thresholdValueEntities.size() > 0) {
                for (ThresholdValueEntity thresholdValueEntity : thresholdValueEntities) {
                    if (dV >= thresholdValueEntity.getMin() && dV <= thresholdValueEntity.getMax()) {
                        // setValueStyle(thresholdValueEntity.getLevel());
                        EquipmentDetailDTO equipmentDetailDTO = new EquipmentDetailDTO(null);
                        setValueStyle(equipmentDetailDTO.CheckThresholdValue(thresholdValueEntities, dV));
                        break;
                    }
                }
            }
        }

    }
}
