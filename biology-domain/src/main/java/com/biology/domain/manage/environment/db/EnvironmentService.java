package com.biology.domain.manage.environment.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.environment.dto.EnvironmentStatisticsDTO;
import com.biology.domain.manage.environment.dto.EnvironmentTypesDTO;
import com.biology.domain.manage.environment.query.DayStatisticsQuery;

public interface EnvironmentService extends IService<EnvironmentEntity> {

    // 获取周统计数据
    public List<EnvironmentStatisticsDTO> getDayStatistics(Long environmentId);

    public EnvironmentTypesDTO getAllGroup();

    public List<String> getAllAreas();

    public List<Long> getAllIds();
}
