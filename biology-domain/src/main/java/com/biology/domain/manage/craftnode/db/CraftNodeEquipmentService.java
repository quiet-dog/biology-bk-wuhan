package com.biology.domain.manage.craftnode.db;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;

// @Service
// public interface CraftNodeService extends IService<CraftNodeEntity> {
// // }
// @Service
// @RequiredArgsConstructor
// public class CraftNodeEquipmentService {

//     private final JdbcTemplate jdbcTemplate;

//     public void saveCraftNodeEquipments(Long craftNodeId, List<Long> equipmentIds) {
//         // 先删除原有关联
//         String deleteSql = "DELETE FROM manage_craft_node_equipment WHERE craft_node_id = ?";
//         jdbcTemplate.update(deleteSql, craftNodeId);

//         if(CollUtil.isEmpty(equipmentIds)) {
//             return;
//         }

//         // 批量插入新关联
//         String insertSql = "INSERT INTO manage_craft_node_equipment (craft_node_id, equipment_id) VALUES (?, ?)";
//         List<Object[]> batchArgs = equipmentIds.stream()
//             .map(equipmentId -> new Object[]{craftNodeId, equipmentId})
//             .collect(Collectors.toList());
//         jdbcTemplate.batchUpdate(insertSql, batchArgs);
//     }

//     public List<Long> getEquipmentIdsByCraftNodeId(Long craftNodeId) {
//         String sql = "SELECT equipment_id FROM manage_craft_node_equipment WHERE craft_node_id = ?";
//         return jdbcTemplate.queryForList(sql, Long.class, craftNodeId);
//     }
// }

// @Service
public interface CraftNodeEquipmentService extends IService<CraftNodeEquipmentEntity> {
    void saveCraftNodeEquipments(Long craftNodeId, List<Long> equipmentIds);

    List<Long> getEquipmentIdsByCraftNodeId(Long craftNodeId);
}
