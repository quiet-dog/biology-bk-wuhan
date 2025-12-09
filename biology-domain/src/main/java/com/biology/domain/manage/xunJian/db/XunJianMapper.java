package com.biology.domain.manage.xunJian.db;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface XunJianMapper extends BaseMapper<XunJianEntity> {

    @Select("select equipment_id from manage_equipment where installation_location in (#{areas})")
    public List<Long> getAllEquipmentByArea(@Param("areas") String areas);

    @Select("select environment_id from manage_environment where e_area in (#{areas})")
    public List<Long> getAllEnvironmentByArea(@Param("areas") String areas);

    @Select("select installation_location from manage_equipment group by installation_location")
    public List<String> getAllEquipmentAreas();

    @Select("select e_area from manage_environment group by e_area")
    public List<String> getAllEnvironmentAreas();
}
