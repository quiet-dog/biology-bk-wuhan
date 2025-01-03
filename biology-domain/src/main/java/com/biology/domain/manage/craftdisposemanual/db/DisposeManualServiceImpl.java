package com.biology.domain.manage.craftdisposemanual.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class DisposeManualServiceImpl extends ServiceImpl<DisposeManualMapper, DisposeManualEntity>
        implements DisposeManualService {

    @Override
    public DisposeManualEntity getById(Long disposeManualId) {
        return super.getById(disposeManualId);
    }
}
