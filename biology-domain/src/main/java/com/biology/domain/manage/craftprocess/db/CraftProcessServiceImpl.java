package com.biology.domain.manage.craftprocess.db;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class CraftProcessServiceImpl extends ServiceImpl<CraftProcessMapper, CraftProcessEntity>
        implements CraftProcessService {
            
    public CraftProcessEntity getById(Long craftProcessId) {
        return baseMapper.selectById(craftProcessId);
    }
}
