package com.biology.domain.manage.environment.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class EnvironmentEmergencyServiceImpl extends ServiceImpl<EnvironmentEmergencyMapper, EnvironmentEmergencyEntity>
        implements EnvironmentEmergencyService {

    public List<Long> getEmergencyIdsByEnvironmentId(Long environmentId) {
        return baseMapper.getEmergencyIdsByEnvironmentId(environmentId);
    }
}
