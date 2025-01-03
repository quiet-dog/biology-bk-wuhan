package com.biology.domain.manage.emergency;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.emergency.command.AddEmergencyCommand;
import com.biology.domain.manage.emergency.command.UpdateEmergencyCommand;
import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.db.EmergencyFileEntity;
import com.biology.domain.manage.emergency.db.EmergencyKeywordEntity;
import com.biology.domain.manage.emergency.db.EmergencyService;
import com.biology.domain.manage.emergency.db.KeyWordEntity;
import com.biology.domain.manage.emergency.db.KeyWordService;
import com.biology.domain.manage.emergency.dto.EmergencyDTO;
import com.biology.domain.manage.emergency.dto.EmergencyDetailDTO;
import com.biology.domain.manage.emergency.model.EmergencyFactory;
import com.biology.domain.manage.emergency.model.EmergencyModel;
import com.biology.domain.manage.emergency.query.SearchEmergencyQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmergencyApplicationService {

    private final EmergencyFactory emergencyFactory;

    private final EmergencyService emergencyService;

    private final KeyWordService keyWordService;

    public void createEmergency(AddEmergencyCommand command) {
        EmergencyModel emergencyModel = emergencyFactory.create();
        emergencyModel.loadAddEmergencyCommand(command);
        emergencyModel.checkEmergencyKeyWord();
        emergencyModel.insert();
    }

    public void updateEmergency(UpdateEmergencyCommand command) {
        EmergencyModel emergencyModel = emergencyFactory.loadById(command.getEmergencyId());
        emergencyModel.loadUpdateEmergencyCommand(command);
        emergencyModel.checkEmergencyKeyWord();
        emergencyModel.updateById();
    }

    public void deleteEmergencies(List<Long> emergencyIds) {
        for (Long emergencyId : emergencyIds) {
            EmergencyModel emergencyModel = emergencyFactory.loadById(emergencyId);
            emergencyModel.deleteById();
        }
    }

    public EmergencyDetailDTO getEmergencyInfo(Long emergencyId) {
        EmergencyEntity emergencyEntity = emergencyService.getById(emergencyId);
        List<KeyWordEntity> keyWordEntities = emergencyService.getKeywordsOfEmergency(emergencyId);
        List<EmergencyFileEntity> emergencyFileEntities = emergencyService.getPathsOfEmergency(emergencyId);

        return new EmergencyDetailDTO(emergencyEntity, emergencyFileEntities, keyWordEntities);
    }

    public PageDTO<EmergencyDTO> getEmergencyList(SearchEmergencyQuery query) {
        Page<EmergencyEntity> page = emergencyService.page(query.toPage(), query.toQueryWrapper());
        List<EmergencyDTO> records = page.getRecords().stream().map(EmergencyDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

}
