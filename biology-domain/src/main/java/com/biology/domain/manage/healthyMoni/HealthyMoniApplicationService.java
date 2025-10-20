package com.biology.domain.manage.healthyMoni;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.healthy.model.HealthyFactory;
import com.biology.domain.manage.healthy.model.HealthyModel;
import com.biology.domain.manage.healthyMoni.command.AddHealthyMoniCommand;
import com.biology.domain.manage.healthyMoni.command.UpdateHealthyMoniCommand;
import com.biology.domain.manage.healthyMoni.db.HealthyMoniEntity;
import com.biology.domain.manage.healthyMoni.db.HealthyMoniService;
import com.biology.domain.manage.healthyMoni.dto.HealthyMoniDTO;
import com.biology.domain.manage.healthyMoni.model.HealthyMoniFactory;
import com.biology.domain.manage.healthyMoni.model.HealthyMoniModel;
import com.biology.domain.manage.healthyMoni.query.HealthyMoniQuery;
import com.biology.domain.manage.task.DynamicTaskScheduler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthyMoniApplicationService {
    private final HealthyMoniFactory healthyMoniFactory;

    private final HealthyMoniService healthyMoniService;

    private final DynamicTaskScheduler dynamicTaskScheduler;

    private final HealthyFactory healthyFactory;

    public void addHealthyMoni(AddHealthyMoniCommand command) {
        HealthyMoniModel healthyMoniModel = healthyMoniFactory.create();
        healthyMoniModel.loadAddHealthyMoniCommand(command);
        healthyMoniModel.insert();
    }

    public void updateHealthyMoni(UpdateHealthyMoniCommand command) {
        HealthyMoniModel healthyMoniModel = healthyMoniFactory.loadById(command.getHealthyMoniId());
        healthyMoniModel.loadAddHealthyMoniCommand(command);
        healthyMoniModel.updateById();

    }

    public void deleteHealthyMonis(List<Long> healthyMoniIds) {
        for (Long healthyMoniId : healthyMoniIds) {
            HealthyMoniModel healthyMoniModel = healthyMoniFactory.loadById(healthyMoniId);
            healthyMoniModel.deleteById();
        }
    }

    public PageDTO<HealthyMoniDTO> getHealthyMoniList(HealthyMoniQuery query) {
        Page<HealthyMoniEntity> page = healthyMoniService.page(query.toPage(), query.toQueryWrapper());
        List<HealthyMoniDTO> records = page.getRecords().stream().map(HealthyMoniDTO::new).collect(Collectors.toList());
        ArrayList<String> fields = new ArrayList<>();
        fields.add("体温");
        fields.add("收缩压");
        fields.add("舒张压");
        fields.add("心率");
        if (page.getTotal() == 0 && query.getPersonnelId() != null && query.getPersonnelId() > 0) {
            for (String v : fields) {
                AddHealthyMoniCommand addHealthyMoniCommand = new AddHealthyMoniCommand();
                addHealthyMoniCommand.setFieldType(v);
                addHealthyMoniCommand.setPersonnelId(query.getPersonnelId());
                addHealthyMoni(addHealthyMoniCommand);
            }
            page = healthyMoniService.page(query.toPage(), query.toQueryWrapper());
            records = page.getRecords().stream().map(HealthyMoniDTO::new).collect(Collectors.toList());
        }

        return new PageDTO<>(records, page.getTotal());
    }

    public HealthyMoniDTO getHealthyMoniInfo(Long healthyMoniId) {
        HealthyMoniModel healthyMoniModel = healthyMoniFactory.loadById(healthyMoniId);
        return new HealthyMoniDTO(healthyMoniModel);
    }

    public void batchUpdateHealthy(List<UpdateHealthyMoniCommand> commands) {
        for (UpdateHealthyMoniCommand command : commands) {
            updateHealthyMoni(command);
        }
        String taskId = String.format("personnel-%d", commands.get(0).getHealthyMoniId());

        dynamicTaskScheduler.removeTask(taskId);
        if (commands.get(0).getIsPush() && commands.get(0).getPushFrequency() != null) {

            dynamicTaskScheduler.addTask(taskId, () -> {
                HealthyModel healthyModel = healthyFactory.create();
                for (UpdateHealthyMoniCommand command : commands) {
                    if (command.getMin() != null && command.getMax() != null && command.getMin() < command.getMax()) {
                        // fields.add("体温");
                        // fields.add("收缩压");
                        // fields.add("舒张压");
                        // fields.add("心率");
                        double random = ThreadLocalRandom.current().nextDouble(command.getMin(), command.getMax());
                        double result = Math.round(random * 100.0) / 100.0;

                        if (command.getFieldType().equals("体温")) {
                            healthyModel.setTemperature(result);
                        }
                        if (command.getFieldType().equals("收缩压")) {
                            healthyModel.setHighBloodPressure(result);
                        }
                        if (command.getFieldType().equals("舒张压")) {
                            healthyModel.setLowBloodPressure(result);
                        }
                        if (command.getFieldType().equals("心率")) {
                            healthyModel.setHeartRate(result);
                        }
                    }
                }
                healthyModel.setPersonnelId(commands.get(0).getPersonnelId());
                healthyModel.insert();
            }, 0, commands.get(0).getPushFrequency(), TimeUnit.SECONDS);
        }
    }
}
