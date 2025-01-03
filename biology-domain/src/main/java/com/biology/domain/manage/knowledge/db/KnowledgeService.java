package com.biology.domain.manage.knowledge.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.system.user.db.SysUserEntity;

public interface KnowledgeService extends IService<KnowledgeEntity> {

    List<String> getKnowledgePaths(Long knowledgeId);

    SysUserEntity getCreater(Long createrId);
}
