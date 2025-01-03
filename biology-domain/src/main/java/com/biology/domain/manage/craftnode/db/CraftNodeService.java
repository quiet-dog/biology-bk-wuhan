package com.biology.domain.manage.craftnode.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;

// @Service
public interface CraftNodeService extends IService<CraftNodeEntity> {
    Long getCraftNodeIdByCode(String nodeCode);
}
