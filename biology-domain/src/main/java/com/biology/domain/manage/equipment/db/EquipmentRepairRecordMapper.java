package com.biology.domain.manage.equipment.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.equipment.dto.RepairRecordEchartDTO;

public interface EquipmentRepairRecordMapper extends BaseMapper<EquipmentRepairRecordEntity> {

    @Select("SELECT COUNT(*) as count,DATE_FORMAT(r.create_time,'%Y-%m') as time FROM manage_equipment_repair_record as r"
            + " JOIN manage_equipment e on e.equipment_id = r.equipment_id"
            + " WHERE e.equipment_code = #{equipmentCode}"
            + " AND r.create_time >= DATE_SUB(CURDATE(), INTERVAL 11 MONTH) AND r.create_time < LAST_DAY(CURDATE()) + INTERVAL 1 DAY"
            + " GROUP BY DATE_FORMAT(r.create_time,'%Y-%m')"
            + " ORDER BY DATE_FORMAT(r.create_time,'%Y-%m')")
    List<RepairRecordEchartDTO> getCountByEquipmentCodeYear(String equipmentCode);

    @Select("SELECT COUNT(*) as count,DATE_FORMAT(r.create_time,'%m-%d') as time FROM manage_equipment_repair_record as r"
            + " JOIN manage_equipment e on e.equipment_id = r.equipment_id"
            + " WHERE e.equipment_code = #{equipmentCode}"
            + " AND  r.create_time >= CURDATE() - INTERVAL 1 MONTH AND r.create_time <= NOW()"
            + " GROUP BY DATE_FORMAT(r.create_time,'%m-%d')"
            + " ORDER BY DATE_FORMAT(r.create_time,'%m-%d')")
    List<RepairRecordEchartDTO> getCountByEquipmentCodeMonth(String equipmentCode);

    @Select("SELECT COUNT(*) as count,DATE_FORMAT(r.create_time,'%m-%d') as time FROM manage_equipment_repair_record as r"
            + " JOIN manage_equipment e on e.equipment_id = r.equipment_id"
            + " WHERE e.equipment_code = #{equipmentCode}"
            + " AND r.create_time >= CURDATE() - INTERVAL 6 DAY AND r.create_time < CURDATE() + INTERVAL 1 DAY"
            + " GROUP BY DATE_FORMAT(r.create_time,'%m-%d')"
            + " ORDER BY DATE_FORMAT(r.create_time,'%m-%d')")
    List<RepairRecordEchartDTO> getCountByEquipmentCodeWeek(String equipmentCode);
}
