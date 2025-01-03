package com.biology.domain.manage.environment.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface EnvironmentSopService extends IService<EnvironmentSopEntity> {
    public List<Long> getSopIdsByEnvironmentId(Long environmentId);
}
