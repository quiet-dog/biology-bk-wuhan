package com.biology.domain.manage.environment.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface EnvironmentEmergencyMapper extends BaseMapper<EnvironmentEmergencyEntity> {

    @Select("SELECT emergency_id "
            + "FROM manage_environment_emergency "
            + "WHERE environment_id = #{environmentId} ")
    public List<Long> getEmergencyIdsByEnvironmentId(Long environmentId);
}
