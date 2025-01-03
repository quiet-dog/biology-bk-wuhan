package com.biology.domain.manage.emergency.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface EmergencyService extends IService<EmergencyEntity> {

    List<KeyWordEntity> getKeywordsOfEmergency(Long emergencyId);

    List<EmergencyFileEntity> getPathsOfEmergency(Long emergencyId);
}
