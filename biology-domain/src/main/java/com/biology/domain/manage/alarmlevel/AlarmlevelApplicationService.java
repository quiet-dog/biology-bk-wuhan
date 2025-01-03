package com.biology.domain.manage.alarmlevel;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.alarmlevel.command.AddAlarmlevelCommand;
import com.biology.domain.manage.alarmlevel.command.UpdateAlarmlevelCommand;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelService;
import com.biology.domain.manage.alarmlevel.dto.AlarmlevelDTO;
import com.biology.domain.manage.alarmlevel.model.AlarmlevelFactory;
import com.biology.domain.manage.alarmlevel.model.AlarmlevelModel;
import com.biology.domain.manage.alarmlevel.query.AlarmlevelQuery;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmlevelApplicationService {

    private final AlarmlevelFactory alarmlevelFactory;

    private final AlarmlevelService alarmlevelService;

    public void addAlarmlevel(AddAlarmlevelCommand command) {
        AlarmlevelModel alarmlevelModel = alarmlevelFactory.create();
        alarmlevelModel.loadAddCommand(command);
        alarmlevelModel.insert();
    }

    public void updateAlarmlevel(UpdateAlarmlevelCommand command) {
        AlarmlevelModel alarmlevelModel = alarmlevelFactory.loadById(command.getAlarmlevelId());
        alarmlevelModel.loadUpdateCommand(command);
        alarmlevelModel.updateById();
    }

    public void deleteAlarmlevels(List<Long> alarmlevelIds) {
        for (Long alarmlevelId : alarmlevelIds) {
            deleteAlarmlevel(alarmlevelId);
        }
    }

    public void deleteAlarmlevel(Long alarmlevelId) {
        AlarmlevelModel alarmlevelModel = alarmlevelFactory.loadById(alarmlevelId);
        alarmlevelModel.deleteById();
    }

    public AlarmlevelDTO getAlarmlevel(Long alarmlevelId) {
        AlarmlevelModel alarmlevelModel = alarmlevelFactory.loadById(alarmlevelId);
        return new AlarmlevelDTO(alarmlevelModel);
    }

    public PageDTO<AlarmlevelDTO> searchAlarmlevels(AlarmlevelQuery query) {
        Page<AlarmlevelEntity> page = alarmlevelService.page(query.toPage(), query.toQueryWrapper());
        List<AlarmlevelDTO> records = page.getRecords().stream().map(AlarmlevelDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public void demo() {
    }
}
