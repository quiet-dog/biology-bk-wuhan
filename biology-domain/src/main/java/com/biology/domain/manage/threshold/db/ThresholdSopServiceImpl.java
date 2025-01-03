package com.biology.domain.manage.threshold.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ThresholdSopServiceImpl extends ServiceImpl<ThresholdSopMapper, ThresholdSopEntity>
                implements ThresholdSopService {

        public List<Long> getSopIdsByThresholdId(Long thresholdId) {
                return baseMapper.getSopIdsByThresholdId(thresholdId);
        }
}
