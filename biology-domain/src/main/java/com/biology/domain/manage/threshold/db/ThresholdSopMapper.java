package com.biology.domain.manage.threshold.db;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface ThresholdSopMapper extends BaseMapper<ThresholdSopEntity> {

    @Select("SELECT sop_id "
            + "FROM manage_threshold_sop "
            + "WHERE threshold_id = #{thresholdId} ")
    public List<Long> getSopIdsByThresholdId(Long thresholdId);
}
