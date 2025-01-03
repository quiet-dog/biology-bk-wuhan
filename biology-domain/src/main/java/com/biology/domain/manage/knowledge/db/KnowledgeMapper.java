package com.biology.domain.manage.knowledge.db;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.system.user.db.SysUserEntity;

public interface KnowledgeMapper extends BaseMapper<KnowledgeEntity> {

    @Select("SELECT path FROM manage_knowledge_file WHERE knowledge_id = #{knowledgeId}")
    List<String> getKnowledgePaths(Long knowledgeId);

    @Select("SELECT * FROM sys_user WHERE user_id = #{createrId}")
    SysUserEntity getCreater(Long createrId);
}
