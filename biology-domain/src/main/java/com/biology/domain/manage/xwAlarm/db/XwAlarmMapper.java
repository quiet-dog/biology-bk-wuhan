package com.biology.domain.manage.xwAlarm.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;

import io.lettuce.core.dynamic.annotation.Param;

public interface XwAlarmMapper extends BaseMapper<XwAlarmEntity> {

    @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm where YEAR(create_time) = YEAR(CURDATE()) AND WEEK(create_time, 1) = WEEK(CURDATE(), 1)"
            + " group by seat_number")
    List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByWeek(@Param("dayType") String dayType);

    @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm where YEAR(create_time) = YEAR(CURDATE())\n"
            + //
            "  AND MONTH(create_time) = MONTH(CURDATE())"
            + " group by seat_number")
    List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByMonth(@Param("dayType") String dayType);

    @Select("select COUNT(*) as value,seat_number as name from manage_xw_alarm where YEAR(create_time) = YEAR(CURDATE())"
            + " group by seat_number")
    List<PingGuJieGuoSeriesDTO> JiWeiBaoJingZhanBiByYear(@Param("dayType") String dayType);

}
