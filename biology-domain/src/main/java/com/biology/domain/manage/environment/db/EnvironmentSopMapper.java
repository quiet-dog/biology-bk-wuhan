package com.biology.domain.manage.environment.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface EnvironmentSopMapper extends BaseMapper<EnvironmentSopEntity> {

    @Select("SELECT sop_id "
            + "FROM manage_environment_sop "
            + "WHERE environment_id = #{environmentId} ")
    public List<Long> getSopIdsByEnvironmentId(Long environmentId);
}
