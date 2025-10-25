package com.biology.domain.manage.xunJianHistory.db;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface XunJianHistoryMapper extends BaseMapper<XunJianHistoryEntity> {

    @Select("select count(*) from manage_xun_jian_event where xun_jian_history_id = #{xunJianHistoryId}")
    public Integer getTotalByHistoryId(@Param("xunJianHistoryId") Long xunJianHistoryId);

}
