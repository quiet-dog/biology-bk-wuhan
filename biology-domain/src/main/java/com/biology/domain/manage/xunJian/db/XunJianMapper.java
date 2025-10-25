package com.biology.domain.manage.xunJian.db;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface XunJianMapper extends BaseMapper<XunJianEntity> {

    @Select("select equipment_id from manage_equipment where installation_location = #{area}")
    public List<Long> getAllEquipmentByArea(@Param("area") String area);

    @Select("select environment_id from manage_environment where e_area = #{area}")
    public List<Long> getAllEnvironmentByArea(@Param("area") String area);
}
