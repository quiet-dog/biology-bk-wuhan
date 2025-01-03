package com.biology.domain.manage.emergency.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class EmergencyServiceImpl extends ServiceImpl<EmergencyMapper, EmergencyEntity> implements EmergencyService {

    @Override
    public List<KeyWordEntity> getKeywordsOfEmergency(Long emergencyId) {
        return baseMapper.getKeywordsOfEmergency(emergencyId);
    }

    @Override
    public List<EmergencyFileEntity> getPathsOfEmergency(Long emergencyId) {
        return baseMapper.getPathsOfEmergency(emergencyId);
    }

}
