package com.biology.domain.manage.environment.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.environment.dto.EnvironmentStatisticsDTO;
import com.biology.domain.manage.environment.dto.EnvironmentTypesDTO;
import com.biology.domain.manage.environment.query.DayStatisticsQuery;

@Service
public class EnvironmentServiceImpl extends ServiceImpl<EnvironmentMapper, EnvironmentEntity>
                implements EnvironmentService {

        // 获取一天每小时的统计数据
        @Override
        public List<EnvironmentStatisticsDTO> getDayStatistics(Long environmentId) {
                return baseMapper.getDayStatistics(environmentId);

        }

        public EnvironmentTypesDTO getAllGroup() {
                EnvironmentTypesDTO result = new EnvironmentTypesDTO();
                result.setArea(baseMapper.getAllArea());
                result.setUnitName(baseMapper.getAllUnitName());
                return result;
        }

        public List<String> getAllAreas() {
                return baseMapper.getAllAreas();
        }
}
