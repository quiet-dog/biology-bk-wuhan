package com.biology.domain.manage.threshold.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.lettuce.core.dynamic.annotation.Param;

public interface ThresholdMapper extends BaseMapper<ThresholdEntity> {

    @Select({
            "<script>",
            "WITH device_log AS ( ",
            "   SELECT threshold_id, create_time, ",
            "          LAG(create_time) OVER (PARTITION BY threshold_id ORDER BY create_time) AS prev_time ",
            "   FROM manage_equipment_data ",
            "   WHERE DATE(create_time) = CURDATE() ",
            "     AND threshold_id IN ",
            "     <foreach collection='list' item='item' open='(' separator=',' close=')'>",
            "         #{item}",
            "     </foreach>",
            ") ",
            "SELECT SEC_TO_TIME(SUM( ",
            "   CASE WHEN TIMESTAMPDIFF(SECOND, prev_time, create_time) &lt; 60 ",
            "        THEN TIMESTAMPDIFF(SECOND, prev_time, create_time) ",
            "        ELSE 0 END )) AS run_time ",
            "FROM device_log",
            "</script>"
    })
    String getRunTime(List<Long> Ids);
}
