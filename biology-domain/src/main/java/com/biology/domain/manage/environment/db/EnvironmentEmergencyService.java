package com.biology.domain.manage.environment.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface EnvironmentEmergencyService extends IService<EnvironmentEmergencyEntity> {

    public List<Long> getEmergencyIdsByEnvironmentId(Long environmentId);
}
