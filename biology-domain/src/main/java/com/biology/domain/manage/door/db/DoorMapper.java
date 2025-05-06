package com.biology.domain.manage.door.db;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface DoorMapper extends BaseMapper<DoorEntity> {
        // 获取今天进入的人数
        @Select("SELECT COUNT(personnel_id) FROM manage_door WHERE DATE_FORMAT(create_time, '%Y-%m-%d') = CURDATE()"
                        + " GROUP BY personnel_id")
        public Integer getDayStatus();

        @Select("SELECT  COUNT(*) AS count FROM manage_door WHERE door_date >= UNIX_TIMESTAMP(CURDATE()) * 1000"
                        + " AND door_date < UNIX_TIMESTAMP(CURDATE() + INTERVAL 1 DAY) * 1000"
                        + " AND enter_status = #{enterStatus}")
        public Integer getChuQinLvDay(String enterStatus);
}
