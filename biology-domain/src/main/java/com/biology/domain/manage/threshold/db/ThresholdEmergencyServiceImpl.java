package com.biology.domain.manage.threshold.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ThresholdEmergencyServiceImpl extends ServiceImpl<ThresholdEmergencyMapper, ThresholdEmergencyEntity>
                implements ThresholdEmergencyService {
        public List<Long> getEmergencyIdsByThresholdId(Long thresholdId) {
                return baseMapper.getEmergencyIdsByThresholdId(thresholdId);
        }
}
