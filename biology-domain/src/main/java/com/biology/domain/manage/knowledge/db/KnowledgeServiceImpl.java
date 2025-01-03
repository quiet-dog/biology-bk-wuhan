package com.biology.domain.manage.knowledge.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.system.user.db.SysUserEntity;

@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, KnowledgeEntity> implements KnowledgeService {

    @Override
    public List<String> getKnowledgePaths(Long knowledgeId) {
        return baseMapper.getKnowledgePaths(knowledgeId);
    }

    @Override
    public SysUserEntity getCreater(Long createrId) {
        return baseMapper.getCreater(createrId);
    }

}
