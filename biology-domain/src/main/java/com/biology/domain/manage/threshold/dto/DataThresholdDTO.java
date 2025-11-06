package com.biology.domain.manage.threshold.dto;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DataThresholdDTO {
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

    @Schema(description = "值")
    @ExcelColumn(name = "值")
    private Object value;

    private String valueStyle;

    @Schema(description = "创建时间")
    private Date createTime;

    public DataThresholdDTO(ThresholdEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            insertValues();
        }
    }

    public void insertValues() {
        // ThresholdValueEntity thresholdValueEntity = new ThresholdValueEntity();
        // QueryWrapper<ThresholdValueEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("threshold_id", getThresholdId());
        setValues(CacheCenter.thresholdValuesCache.getObjectById(getThresholdId()));
    }

}
