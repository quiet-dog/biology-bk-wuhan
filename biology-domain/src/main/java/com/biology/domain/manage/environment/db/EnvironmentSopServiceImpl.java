package com.biology.domain.manage.environment.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class EnvironmentSopServiceImpl extends ServiceImpl<EnvironmentSopMapper, EnvironmentSopEntity>
        implements EnvironmentSopService {

    public List<Long> getSopIdsByEnvironmentId(Long environmentId) {
        return baseMapper.getSopIdsByEnvironmentId(environmentId);
    }
}
