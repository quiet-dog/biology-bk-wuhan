package com.biology.domain.manage.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.environment.command.AddEnvironmentCommand;
import com.biology.domain.manage.environment.command.UpdateEnvironmentCommand;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.db.EnvironmentService;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;
import com.biology.domain.manage.environment.dto.EnvironmentEchartDTO;
import com.biology.domain.manage.environment.dto.EnvironmentStatisticsDTO;
import com.biology.domain.manage.environment.dto.EnvironmentTypesDTO;
import com.biology.domain.manage.environment.model.EnvironmentFactory;
import com.biology.domain.manage.environment.model.EnvironmentModel;
import com.biology.domain.manage.environment.query.DayStatisticsQuery;
import com.biology.domain.manage.environment.query.EnvironmentQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnvironmentApplicationService {
    private final EnvironmentFactory environmentFactory;

    private final EnvironmentService environmentService;

    public void addEnvironment(AddEnvironmentCommand command) {
        EnvironmentModel environmentModel = environmentFactory.create();
        environmentModel.loadAddEnvironmentCommand(command);
        environmentModel.insert();
    }

    public void updateEnvironment(UpdateEnvironmentCommand command) {
        EnvironmentModel environmentModel = environmentFactory.loadById(command.getEnvironmentId());
        environmentModel.loadUpdateEnvironmentCommand(command);
        environmentModel.updateById();
    }

    public void deleteEnvironments(List<Long> environmentIds) {
        for (Long environmentId : environmentIds) {
            deleteEnvironment(environmentId);
        }
    }

    public void deleteEnvironment(Long environmentId) {
        EnvironmentModel environmentModel = environmentFactory.loadById(environmentId);
        environmentModel.deleteById();
    }

    public EnvironmentDTO getEnvironment(Long environmentId) {
        EnvironmentModel environmentModel = environmentFactory.loadById(environmentId);
        return new EnvironmentDTO(environmentModel);
    }

    public PageDTO<EnvironmentDTO> searchEnvironments(EnvironmentQuery query) {
        Page<EnvironmentEntity> page = environmentService.page(query.toPage(), query.toQueryWrapper());
        List<EnvironmentDTO> records = page.getRecords().stream().map(EnvironmentDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public List<EnvironmentEchartDTO> getDayStatistics(Long environmentId) {
        List<EnvironmentStatisticsDTO> dayStatistics = environmentService.getDayStatistics(environmentId);
        List<EnvironmentEchartDTO> result = new ArrayList<>();
        for (EnvironmentStatisticsDTO statistics : dayStatistics) {
            boolean isExit = false;
            for (EnvironmentEchartDTO echart : result) {
                if (echart.getUnit().equals(statistics.getUnit())) {
                    isExit = true;
                    echart.getData().add(statistics);
                }
            }
            if (!isExit) {
                EnvironmentEchartDTO echart = new EnvironmentEchartDTO();
                echart.setUnit(statistics.getUnit());
                echart.setData(new ArrayList<>());
                echart.getData().add(statistics);
                result.add(echart);
            }
        }
        return result;
    }

    public EnvironmentTypesDTO getAllGroup() {
        return environmentService.getAllGroup();
    }

    public List<String> getAllAreas() {
        return environmentService.getAllAreas();
    }
}
