package com.biology.domain.manage.craftdisposemanual.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;

@Service
public interface DisposeManualService extends IService<DisposeManualEntity> {

    DisposeManualEntity getById(Long disposeManualId);

}
