package com.biology.domain.manage.emergency.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface EmergencyMapper extends BaseMapper<EmergencyEntity> {

    @Select("SELECT k.* FROM manage_keyword k"
            + " LEFT JOIN manage_emergency_keyword ek ON ek.keyword_id = k.keyword_id "
            + "WHERE ek.emergency_id = #{emergencyId}")
    List<KeyWordEntity> getKeywordsOfEmergency(Long emergencyId);

    @Select("SELECT * FROM manage_emergency_file WHERE emergency_id = #{emergencyId}")
    List<EmergencyFileEntity> getPathsOfEmergency(Long emergencyId);

}
