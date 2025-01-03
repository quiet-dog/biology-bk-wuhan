package com.biology.domain.manage.craftnode.db;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class CraftNodeServiceImpl extends ServiceImpl<CraftNodeMapper, CraftNodeEntity>
                implements CraftNodeService {

    @Override
    public Long getCraftNodeIdByCode(String nodeCode) {
        return lambdaQuery().eq(CraftNodeEntity::getNodeCode, nodeCode).oneOpt().map(CraftNodeEntity::getCraftNodeId).orElse(0L);
    }
}
