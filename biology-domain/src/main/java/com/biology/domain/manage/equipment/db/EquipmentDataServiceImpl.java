package com.biology.domain.manage.equipment.db;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockDTO;
import com.biology.domain.manage.equipment.dto.EquipmentDataStockEchartDTO;
import com.biology.domain.manage.equipment.dto.TotalTimeDTO;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class EquipmentDataServiceImpl extends ServiceImpl<EquipmentDataMapper, EquipmentDataEntity>
        implements EquipmentDataService {

    @Resource
    private JdbcTemplate jdbcTemplate; // 注入 JdbcTemplate

    @Override
    public EquipmentDataEntity getById(Long equipmentId) {
        return super.getById(equipmentId);
    }

    @Override
    public EquipmentDataStockEchartDTO getEquipmentDataStockDay(Long threshold) {
        EquipmentDataStockEchartDTO echartDTO = new EquipmentDataStockEchartDTO();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String suffix = sdf.format(date);
        List<EquipmentDataStockDTO> list = baseMapper.getEquipmentDataStockDay(suffix, threshold);
        echartDTO.setData(new ArrayList<>());
        echartDTO.setTime(DatePickUtil.getDayNowHalfHour());
        for (String s : echartDTO.getTime()) {
            Boolean isExit = false;
            for (EquipmentDataStockDTO equipmentDataStockDTO : list) {
                if (s.equals(equipmentDataStockDTO.getTime())) {
                    isExit = true;
                    Double value = equipmentDataStockDTO.getData();
                    if (value != null) {
                        value = Math.round(value * 100.0) / 100.0;
                    } else {
                        value = null;
                    }
                    echartDTO.getData().add(value);
                    break;
                }
            }
            if (!isExit) {
                echartDTO.getData().add(0.0);
            }
        }

        ThresholdEntity tEntity = new ThresholdEntity().selectById(threshold);
        if (tEntity != null) {
            echartDTO.setUnitName(tEntity.getUnit());
            echartDTO.setSensorName(tEntity.getSensorName());

            EquipmentEntity eEntity = new EquipmentEntity().selectById(tEntity.getEquipmentId());
            if (eEntity != null) {
                echartDTO.setEquipmentName(eEntity.getEquipmentName());
                echartDTO.setEquipmentCode(eEntity.getEquipmentCode());
            }
        }
        // for (EquipmentDataStockDTO equipmentDataStockDTO : list) {
        // echartDTO.getData().add(equipmentDataStockDTO.getData());
        // echartDTO.getTime().add(equipmentDataStockDTO.getTime());
        // }
        return echartDTO;
    }

    public TotalTimeDTO getTotalTime(Long equipmentId) {
        TotalTimeDTO timeDTO = new TotalTimeDTO();
        Integer value = baseMapper.getTotalTime(equipmentId);
        timeDTO.setTotalTime(value);
        return timeDTO;
    }

    public Map<String, Object> getEquipmentDataByEquipmentId(Long threshold, String dayTime) {
        Map<String, Object> map = new HashMap<>();
        List<EquipmentDataStockDTO> list = baseMapper.getHistoryCurentDay(threshold, dayTime);
        // if (list == null) {
        // list = Collections.emptyList();
        // }
        // dayTime是2025-09-18这种格式,判断是否是今天,如果是今天的话,将list的time大于当前time的数据去掉
        List<String> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        for (EquipmentDataStockDTO item : list) {
            if (item != null) {
                xData.add(item.getTime());
                yData.add(item.getData());
            }
        }

        ThresholdEntity tEntity = new ThresholdEntity().selectById(threshold);
        map.put("xData", xData);
        map.put("yData", yData);
        map.put("unit", tEntity != null ? tEntity.getUnit() : "未知");
        map.put("list", list);

        return map;
    }

    public void createNowTable() {
        String tableName = "manage_equipment_data_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS `%s` (" +
                        "`equipment_data_id` bigint NOT NULL AUTO_INCREMENT COMMENT '设备数据ID'," +
                        "`equipment_id` bigint NOT NULL COMMENT '设备ID'," +
                        "`threshold_id` bigint DEFAULT NULL COMMENT '传感器阈值ID'," +
                        "`monitoring_indicator` varchar(255) DEFAULT NULL COMMENT '监测指标'," +
                        "`equipment_data` double DEFAULT NULL COMMENT '设备数据'," +
                        "`remark` text COMMENT '注'," +
                        "`creator_id` bigint DEFAULT NULL COMMENT '创建者ID'," +
                        "`updater_id` bigint DEFAULT NULL COMMENT '更新者ID'," +
                        "`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'," +
                        "`update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'," +
                        "`deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）'," +
                        "PRIMARY KEY (`equipment_data_id`)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备数据表'",
                tableName);

        jdbcTemplate.execute(sql); // 执行 SQL

        tableName = "manage_environment_detection_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        sql = String.format(
                "CREATE TABLE IF NOT EXISTS `%s` (" +
                        "  `detection_id` bigint NOT NULL AUTO_INCREMENT," +
                        "  `environment_id` bigint DEFAULT NULL," +
                        "  `power` double DEFAULT NULL," +
                        "  `value` double DEFAULT NULL," +
                        "  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID'," +
                        "  `create_time` datetime DEFAULT NULL COMMENT '创建时间'," +
                        "  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID'," +
                        "  `update_time` datetime DEFAULT NULL COMMENT '更新时间'," +
                        "  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）'," +
                        "  `water_value` double DEFAULT NULL," +
                        "  `electricity_value` double DEFAULT NULL," +
                        "  `type` varchar(100) DEFAULT NULL," +
                        "  PRIMARY KEY (`detection_id`)" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;",
                tableName);

        jdbcTemplate.execute(sql); // 执行 SQL
        System.out.println("✅ 表创建成功：" + tableName);
    }

    public boolean insertDynamic(EquipmentDataEntity entity) {
        if (entity == null) {
            return false;
        }

        String tableName = "manage_equipment_data_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String sql = String.format(
                "INSERT INTO `%s` (equipment_id, threshold_id, monitoring_indicator, equipment_data, remark) " +
                        "VALUES (?, ?, ?, ?, ?)",
                tableName);
        int rows = jdbcTemplate.update(sql,
                entity.getEquipmentId(),
                entity.getThresholdId(),
                entity.getMonitoringIndicator(),
                entity.getEquipmentData(),
                entity.getRemark());
        return rows > 0;
    }

}
