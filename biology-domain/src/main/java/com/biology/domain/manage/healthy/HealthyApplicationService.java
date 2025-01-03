package com.biology.domain.manage.healthy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.healthy.command.AddHealthyCommand;
import com.biology.domain.manage.healthy.command.UpdateHealthyCommand;
import com.biology.domain.manage.healthy.db.HealthyAlarmService;
import com.biology.domain.manage.healthy.db.HealthyEntity;
import com.biology.domain.manage.healthy.db.HealthyService;
import com.biology.domain.manage.healthy.dto.HealthyAlarmEchartDTO;
import com.biology.domain.manage.healthy.dto.HealthyDTO;
import com.biology.domain.manage.healthy.dto.HealthyStockEchartDTO;
import com.biology.domain.manage.healthy.model.HealthyFactory;
import com.biology.domain.manage.healthy.model.HealthyModel;
import com.biology.domain.manage.healthy.query.HealthyAlarmQuery;
import com.biology.domain.manage.healthy.query.HealthyOneQuery;
import com.biology.domain.manage.healthy.query.HealthyQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthyApplicationService {
    private final HealthyFactory healthyFactory;

    private final HealthyService healthyService;

    private final HealthyAlarmService healthyAlarmService;

    public void addHealthy(AddHealthyCommand command) {
        HealthyModel healthyModel = healthyFactory.create();
        healthyModel.loadAddHealthyCommand(command);
        healthyModel.insert();
    }

    public void updateHealthy(UpdateHealthyCommand command) {
        HealthyModel healthyModel = healthyFactory.loadById(command.getHealthyId());
        healthyModel.loadAddHealthyCommand(command);
        healthyModel.updateById();
    }

    public void deleteHealthys(List<Long> healthyIds) {
        for (Long healthyId : healthyIds) {
            HealthyModel healthyModel = healthyFactory.loadById(healthyId);
            healthyModel.deleteById();
        }
    }

    public PageDTO<HealthyDTO> getHealthyList(HealthyQuery query) {
        Page<HealthyEntity> page = healthyService.page(query.toPage(), query.toQueryWrapper());
        List<HealthyDTO> records = page.getRecords().stream().map(HealthyDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public HealthyDTO getHealthyInfo(Long healthyId) {
        HealthyModel healthyModel = healthyFactory.loadById(healthyId);
        return new HealthyDTO(healthyModel);
    }

    public List<HealthyAlarmEchartDTO> getAlarmStockType(HealthyAlarmQuery query) {
        List<HealthyAlarmEchartDTO> list = healthyAlarmService.getHealthyAlarmTypeStatic(query);
        return list;
    }

    public HealthyStockEchartDTO getHealthyStock(HealthyOneQuery query) {
        return healthyService.getHealthyStock(query);
    }

}
