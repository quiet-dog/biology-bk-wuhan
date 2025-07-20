package com.biology.domain.manage.personnel.db;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface PersonnelMapper extends BaseMapper<PersonnelEntity> {

        @Select("SELECT * "
                        + "FROM manage_personnel "
                        + "WHERE code = #{code} ")
        PersonnelEntity getPersonnelByCode(String code);

        @Select("SELECT COUNT(*) "
                        + "FROM manage_personnel Where deleted = 0")
        Integer getAllCount();
}
