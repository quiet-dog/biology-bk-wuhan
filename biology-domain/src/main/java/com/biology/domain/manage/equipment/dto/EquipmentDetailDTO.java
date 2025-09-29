package com.biology.domain.manage.equipment.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.threshold.dto.ThresholdDTO;
import com.biology.domain.manage.websocket.dto.OnlineDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class EquipmentDetailDTO {
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

    private List<SensorDTO> sensorList;

    private String dataTime;

    @Data
    public class SensorDTO {
        private Long thresholdId;
        private String key;
        private Object data;
        private Boolean isOnline;
        private Object value;
        private String unit;
        private String color;
    }

    public EquipmentDetailDTO(EquipmentEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            String redisId = "equipment-" + entity.getEquipmentId();
            OnlineDTO onlineDTO = CacheCenter.onlineCache.getObjectOnlyInCacheById(redisId);
            if (onlineDTO != null) {
                setIsOnline(true);
                // setDataTime(new Date(onlineDTO.getTime()).toString());
                // 将时间转为yyyy-MM-dd HH:mm:ss
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                setDataTime(sdf.format(new Date(onlineDTO.getTime())));
            } else {
                setIsOnline(false);
                setDataTime(null);
            }
            addThresholdList();
        }
    }

    public void addThresholdList() {
        if (getEquipmentId() != null) {
            QueryWrapper<ThresholdEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("equipment_id", getEquipmentId());
            List<ThresholdEntity> thresholdEntities = new ThresholdEntity().selectList(queryWrapper);
            // thresholdList = thresholdEntities;
            if (thresholdEntities != null && thresholdEntities.size() > 0) {
                sensorList = new ArrayList<>();
                for (ThresholdEntity thresholdEntity : thresholdEntities) {
                    SensorDTO sensorDTO = new SensorDTO();
                    sensorDTO.setThresholdId(thresholdEntity.getThresholdId());
                    sensorDTO.setKey(thresholdEntity.getEquipmentIndex());

                    String redisId = "threshold-" + thresholdEntity.getThresholdId();
                    OnlineDTO onlineDTO = CacheCenter.onlineCache.getObjectById(redisId);
                    if (onlineDTO != null) {
                        sensorDTO.setData(onlineDTO.getThresholdData() + thresholdEntity.getUnit());
                        sensorDTO.setValue(onlineDTO.getThresholdData());
                        sensorDTO.setUnit(thresholdEntity.getUnit());
                    } else {
                        sensorDTO.setValue("暂无数据");
                    }

                    List<ThresholdValueEntity> thresholdValueEntities = CacheCenter.thresholdValuesCache
                            .getObjectById(thresholdEntity.getThresholdId());
                    // 判断value是否可以转为Double
                    if (sensorDTO.getValue() != null && sensorDTO.getValue() instanceof Double) {
                        sensorDTO.setColor(CheckThresholdValue(thresholdValueEntities,
                                Double.parseDouble(sensorDTO.getValue().toString())));
                    } else {
                        sensorDTO.setColor("white");
                    }

                    sensorList.add(sensorDTO);
                }
            }
        }
    }

    public String CheckThresholdValue(List<ThresholdValueEntity> thresholdValueEntities, Double value) {

        Map<String, Map<String, String>> colorMap = new HashMap<>();

        colorMap.put("一级", new HashMap<String, String>() {
            {
                put("color", "#F53F3F");
                put("backgroundColor", "#FFECE8");
                put("borderColor", "#F53F3F");
            }
        });

        colorMap.put("紧急", new HashMap<String, String>() {
            {
                put("color", "#F53F3F");
                put("backgroundColor", "#FFECE8");
                put("borderColor", "#F53F3F");
            }
        });

        colorMap.put("二级", new HashMap<String, String>() {
            {
                put("color", "#FF7D00");
                put("backgroundColor", "#FFF3E8");
                put("borderColor", "#FF7D00");
            }
        });

        colorMap.put("重要", new HashMap<String, String>() {
            {
                put("color", "#FF7D00");
                put("backgroundColor", "#FFF3E8");
                put("borderColor", "#FF7D00");
            }
        });

        colorMap.put("三级", new HashMap<String, String>() {
            {
                put("color", "#B99E00");
                put("backgroundColor", "#FFF7CC");
                put("borderColor", "#FADC19");
            }
        });

        colorMap.put("中度", new HashMap<String, String>() {
            {
                put("color", "#B99E00");
                put("backgroundColor", "#FFF7CC");
                put("borderColor", "#FADC19");
            }
        });

        colorMap.put("四级", new HashMap<String, String>() {
            {
                put("color", "#168CFF");
                put("backgroundColor", "#E8F3FF");
                put("borderColor", "#168CFF");
            }
        });

        colorMap.put("一般", new HashMap<String, String>() {
            {
                put("color", "#168CFF");
                put("backgroundColor", "#E8F3FF");
                put("borderColor", "#168CFF");
            }
        });

        colorMap.put("五级", new HashMap<String, String>() {
            {
                put("color", "#00B42A");
                put("backgroundColor", "#E8FFEA");
                put("borderColor", "#00B42A");
            }
        });

        colorMap.put("轻微", new HashMap<String, String>() {
            {
                put("color", "#00B42A");
                put("backgroundColor", "#E8FFEA");
                put("borderColor", "#00B42A");
            }
        });

        for (ThresholdValueEntity thresholdValueEntity : thresholdValueEntities) {
            if (value >= thresholdValueEntity.getMin() && value <= thresholdValueEntity.getMax()) {
                Map<String, String> color = colorMap.get(thresholdValueEntity.getLevel());
                if (color != null) {
                    return color.get("color");
                }
                return "white";
            }
        }
        return "white";
    }
}
