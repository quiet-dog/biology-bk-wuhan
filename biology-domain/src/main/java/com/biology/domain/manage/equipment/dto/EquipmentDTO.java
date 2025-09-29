package com.biology.domain.manage.equipment.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.dto.ThresholdDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EquipmentDTO {

    @Schema(description = "设备ID")
    private Long equipmentId;

    @Schema(description = "设备编号")
    @ExcelColumn(name = "设备编号")
    private String equipmentCode;

    @Schema(description = "设备名称")
    @ExcelColumn(name = "设备名称")
    private String equipmentName;

    @Schema(description = "传感器列表")
    private List<ThresholdEntity> thresholdList;

    @Schema(description = "设备型号")
    @ExcelColumn(name = "设备型号")
    private String equipmentType;

    @Schema(description = "生产厂家")
    @ExcelColumn(name = "生产厂家")
    private String manufacturer;

    @Schema(description = "购置日期")
    @ExcelColumn(name = "购置日期")
    private Date purchaseDate;

    @Schema(description = "安装位置")
    @ExcelColumn(name = "安装位置")
    private String installationLocation;

    @Schema(description = "使用状态")
    private String usageStatus;

    @Schema(description = "技术参数")
    @ExcelColumn(name = "技术参数")
    private String technicalSpec;

    @Schema(description = "性能参数")
    @ExcelColumn(name = "性能参数")
    private String performanceParams;

    @Schema(description = "是否在线")
    private Boolean isOnline;

    public EquipmentDTO(EquipmentEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            String redisId = "equipment-" + entity.getEquipmentId();
            isOnline = CacheCenter.onlineCache.getObjectOnlyInCacheById(redisId) != null;
            addThresholdList();
        }
    }

    public void addThresholdList() {
        if (getEquipmentId() != null) {
            QueryWrapper<ThresholdEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("equipment_id", getEquipmentId());
            List<ThresholdEntity> thresholdEntities = new ThresholdEntity().selectList(queryWrapper);
            thresholdList = thresholdEntities;

        }
    }
}
