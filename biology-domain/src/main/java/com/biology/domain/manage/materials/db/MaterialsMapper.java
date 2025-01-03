package com.biology.domain.manage.materials.db;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface MaterialsMapper extends BaseMapper<MaterialsEntity> {

    @Select("SELECT * "
            + "FROM manage_materials "
            + "WHERE code = #{code} ")
    MaterialsEntity getMaterialsByCode(String code);

    // public void getMaterialsByUseType();

}
