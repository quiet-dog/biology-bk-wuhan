package com.biology.domain.manage.xlFangAn.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface XlFangAnMapper extends BaseMapper<XlFangAnEntity> {

    @Select("SELECT " +
            "COALESCE(p.department, '未知') AS department " +
            "FROM manage_xl_archive xl " +
            "LEFT JOIN manage_personnel p ON p.personnel_id = xl.personnel_id " +
            "AND p.deleted = 0 " +
            "WHERE xl.deleted = 0 " +
            "GROUP BY department")
    List<String> getPersonnelGroup(); // 注意这里是方法，不是字段
}
