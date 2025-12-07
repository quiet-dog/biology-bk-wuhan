package com.biology.domain.manage.xunJianHistory.db;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface XunJianEventMapper extends BaseMapper<XunJianEventEntity> {

    // 统计最近7天每天的报警数量,只需要数量和日期
    @Select("select count(*) as count, DATE_FORMAT(create_time, '%Y-%m-%d') as date from manage_xun_jian_event where create_time >= CURDATE() - INTERVAL 7 DAY group by DATE_FORMAT(create_time, '%Y-%m-%d')")
    public List<Map<String, Object>> getAlarmCountByRecent7Days();

    // 最近30天每天的报警数量,只需要数量和日期
    @Select("select count(*) as count, DATE_FORMAT(create_time, '%Y-%m-%d') as date from manage_xun_jian_event where create_time >= CURDATE() - INTERVAL 30 DAY group by DATE_FORMAT(create_time, '%Y-%m-%d')")
    public List<Map<String, Object>> getAlarmCountByRecent30Days();

    // 最近一年每天的报警数量,只需要数量和日期
    @Select("select count(*) as count, DATE_FORMAT(create_time, '%Y-%m-%d') as date from manage_xun_jian_event where create_time >= CURDATE() - INTERVAL 1 YEAR group by DATE_FORMAT(create_time, '%Y-%m-%d')")
    public List<Map<String, Object>> getAlarmCountByRecent1Year();
}
