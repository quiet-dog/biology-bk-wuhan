package com.biology.domain.manage.threshold.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface ThresholdEmergencyMapper extends BaseMapper<ThresholdEmergencyEntity> {

    @Select("SELECT emergency_id "
            + "FROM manage_threshold_emergency "
            + "WHERE threshold_id = #{thresholdId} ")
    public List<Long> getEmergencyIdsByThresholdId(Long thresholdId);
}
