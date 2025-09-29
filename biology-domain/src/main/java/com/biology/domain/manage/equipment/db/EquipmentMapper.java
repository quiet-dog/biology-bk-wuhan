package com.biology.domain.manage.equipment.db;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface EquipmentMapper extends BaseMapper<EquipmentEntity> {

    // @Select("select count(distinct t.equipment_id) "
    // + "from manage_event m "
    // + "left join manage_threshold t on t.threshold_id = m.threshold_id "
    // + "where m.type = '设备报警' "
    // + "AND DATE(m.create_time) = CURDATE()")
    @Select("select count(distinct threshold_id) "
            + "from manage_event "
            + "where type = '设备报警' "
            + "AND DATE(create_time) = CURDATE()")
    public Long getAlarmCountByDay();

    // 统计本月的
    // @Select("select count(distinct t.equipment_id) "
    // + "from manage_event m "
    // + "left join manage_threshold t on t.threshold_id = m.threshold_id "
    // + "where m.type = '设备报警' "
    // + "AND DATE_FORMAT(m.create_time, '%Y-%m') = DATE_FORMAT(CURDATE(),
    // '%Y-%m')")
    @Select("select count(distinct threshold_id) "
            + "from manage_event "
            + "where type = '设备报警' "
            + "AND DATE_FORMAT(create_time, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m')")
    public Long getAlarmCountByMonth();

    // 统计今年的
    // @Select("select count(distinct t.equipment_id) "
    // + "from manage_event m "
    // + "left join manage_threshold t on t.threshold_id = m.threshold_id "
    // + "where m.type = '设备报警' "
    // + "AND DATE_FORMAT(m.create_time, '%Y') = DATE_FORMAT(CURDATE(), '%Y')")
    @Select("select count(distinct threshold_id) "
            + "from manage_event "
            + "where type = '设备报警' "
            + "AND DATE_FORMAT(create_time, '%Y') = DATE_FORMAT(CURDATE(), '%Y')")
    public Long getAlarmCountByYear();
}
