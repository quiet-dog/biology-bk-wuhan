package com.biology.domain.manage.policies.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface PoliciesMapper extends BaseMapper<PoliciesEntity> {
    @Select("SELECT path FROM manage_policies_appendix WHERE policies_id = #{policiesId}")
    List<String> getAppendixPaths(Long policiesId);
}
